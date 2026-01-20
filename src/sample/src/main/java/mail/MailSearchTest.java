package mail;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.search.FlagTerm;
import jakarta.mail.util.SharedByteArrayInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;

/**
 * Demonstrates reading emails using POP3 and IMAP protocols with Jakarta Mail.
 * Shows how to fetch unseen messages, sort by date, and handle attachments.
 * <p>
 * Required dependencies:
 * - jakarta.mail-api
 * - commons-io
 * - commons-lang3
 */
public class MailSearchTest {

    private static final int DEFAULT_MESSAGE_LIMIT = 3;
    private static final int BUFFER_SIZE = 4096;
    private static final String DEFAULT_ATTACHMENT_DIR = "tmp";

    public static void main(String[] args) {
        // Configuration should be provided via arguments or environment variables
        if (args.length < 4) {
            System.out.println("Usage: MailSearchTest <host> <protocol> <user> <password>");
            System.out.println("  protocol: 'pop3' or 'imap'");
            System.out.println("Example: MailSearchTest imaps.example.com imap user@example.com password");
            return;
        }

        String host = args[0];
        String protocol = args[1];
        String user = args[2];
        String password = args[3];

        if ("imap".equalsIgnoreCase(protocol)) {
            readMailIMAP(host, user, password);
        } else if ("pop3".equalsIgnoreCase(protocol)) {
            readMailPOP3(host, user, password);
        } else {
            System.out.println("Unsupported protocol: " + protocol);
        }
    }

    /**
     * Reads emails using POP3 protocol.
     * Note: POP3 does not distinguish between read/unread/recent messages reliably.
     */
    public static void readMailPOP3(String host, String user, String password) {
        Properties properties = new Properties();
        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");

        Session emailSession = Session.getDefaultInstance(properties);

        try (CloseableMailResources resources = new CloseableMailResources(
                emailSession.getStore("pop3s"), host, user, password, "INBOX", Folder.READ_ONLY)) {

            Message[] messages = fetchUnseenMessages(resources.folder);
            sortByDateDescending(messages);
            printMessages(messages, false);

        } catch (MessagingException e) {
            System.err.println("Mail error: " + e.getMessage());
        }
    }

    /**
     * Reads emails using IMAP protocol with attachment support.
     * IMAP properly supports read/unread status and folder operations.
     */
    public static void readMailIMAP(String host, String user, String password) {
        Properties properties = new Properties();
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.starttls.enable", "true");

        Session emailSession = Session.getDefaultInstance(properties);

        try (CloseableMailResources resources = new CloseableMailResources(
                emailSession.getStore("imaps"), host, user, password, "INBOX", Folder.READ_WRITE)) {

            Message[] messages = fetchUnseenMessages(resources.folder);
            sortByDateDescending(messages);
            printMessages(messages, true);

            System.out.println("Done");

        } catch (MessagingException e) {
            System.err.println("Mail error: " + e.getMessage());
        }
    }

    /**
     * Fetches unseen (unread) messages from the folder.
     */
    private static Message[] fetchUnseenMessages(Folder folder) throws MessagingException {
        Flags seen = new Flags(Flags.Flag.SEEN);
        FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
        return folder.search(unseenFlagTerm);
    }

    /**
     * Sorts messages by sent date in descending order (newest first).
     */
    private static void sortByDateDescending(Message[] messages) {
        Comparator<Message> byDateDescending = (m1, m2) -> {
            try {
                return m2.getSentDate().compareTo(m1.getSentDate());
            } catch (MessagingException e) {
                throw new RuntimeException("Error comparing message dates", e);
            }
        };
        Arrays.sort(messages, byDateDescending);
    }

    /**
     * Prints message details and optionally processes attachments.
     */
    private static void printMessages(Message[] messages, boolean processAttachments) throws MessagingException {
        System.out.println("Total messages: " + messages.length);

        int limit = Math.min(messages.length, DEFAULT_MESSAGE_LIMIT);
        for (int i = 0; i < limit; i++) {
            Message message = messages[i];
            printMessageDetails(message, i + 1);

            if (processAttachments) {
                processMessageContent(message);
            }
        }
    }

    /**
     * Prints basic message information.
     */
    private static void printMessageDetails(Message message, int messageNumber) throws MessagingException {
        System.out.println("---------------------------------");
        System.out.println("Email Number: " + messageNumber);
        System.out.println("Subject: " + message.getSubject());
        System.out.println("From: " + message.getFrom()[0]);
        System.out.println("Received: " + message.getReceivedDate());
        System.out.println("Sent: " + message.getSentDate());

        Flags flags = message.getFlags();
        System.out.println("Seen: " + flags.contains(Flags.Flag.SEEN));
        System.out.println("Flagged: " + flags.contains(Flags.Flag.FLAGGED));
        System.out.println("Recent: " + flags.contains(Flags.Flag.RECENT));

        String[] userFlags = flags.getUserFlags();
        if (userFlags.length > 0) {
            System.out.println("User Flags: " + Arrays.toString(userFlags));
        }
    }

    /**
     * Processes message content and extracts attachments.
     * Note: When folder is opened as READ_WRITE, accessing content marks message as read.
     */
    private static void processMessageContent(Message message) {
        try {
            Object content = message.getContent();

            if (content instanceof String body) {
                System.out.println("Body: " + body);
            } else if (content instanceof MimeMultipart mimeMultipart) {
                processMultipart(mimeMultipart);
            } else if (content instanceof Multipart multipart) {
                processMultipart(multipart);
            } else if (content instanceof SharedByteArrayInputStream sharedStream) {
                processSharedStream(sharedStream);
            }
        } catch (MessagingException | IOException e) {
            System.err.println("Error processing message content: " + e.getMessage());
        }
    }

    /**
     * Processes multipart content, extracting attachments.
     */
    private static void processMultipart(Multipart multipart) throws MessagingException, IOException {
        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);

            if (isAttachment(bodyPart)) {
                saveAttachment(bodyPart);
            }
        }
    }

    /**
     * Checks if a body part is an attachment.
     */
    private static boolean isAttachment(BodyPart bodyPart) throws MessagingException {
        String disposition = bodyPart.getDisposition();
        String fileName = bodyPart.getFileName();
        return Part.ATTACHMENT.equalsIgnoreCase(disposition) || StringUtils.isNotBlank(fileName);
    }

    /**
     * Saves an attachment to the default attachment directory.
     */
    private static void saveAttachment(BodyPart bodyPart) throws MessagingException, IOException {
        String fileName = bodyPart.getFileName();
        if (StringUtils.isBlank(fileName)) {
            return;
        }

        Path attachmentDir = Path.of(DEFAULT_ATTACHMENT_DIR);
        Files.createDirectories(attachmentDir);
        Path filePath = attachmentDir.resolve(fileName);

        try (InputStream is = bodyPart.getInputStream();
             OutputStream os = Files.newOutputStream(filePath)) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }

        System.out.println("Attachment saved: " + filePath);
    }

    /**
     * Processes SharedByteArrayInputStream content.
     */
    private static void processSharedStream(SharedByteArrayInputStream sharedStream) throws IOException {
        try (InputStream in = sharedStream.newStream(0, -1);
             StringWriter writer = new StringWriter()) {

            IOUtils.copy(in, writer, StandardCharsets.UTF_8.name());
            System.out.println("Content: " + writer);
        }
    }

    /**
     * Helper class for automatic resource cleanup of mail connections.
     */
    private static class CloseableMailResources implements AutoCloseable {
        final Store store;
        final Folder folder;

        CloseableMailResources(Store store, String host, String user, String password,
                               String folderName, int mode) throws MessagingException {
            this.store = store;
            this.store.connect(host, user, password);
            this.folder = store.getFolder(folderName);
            this.folder.open(mode);
        }

        @Override
        public void close() {
            try {
                if (folder != null && folder.isOpen()) {
                    folder.close(false);
                }
            } catch (MessagingException e) {
                System.err.println("Error closing folder: " + e.getMessage());
            }
            try {
                if (store != null && store.isConnected()) {
                    store.close();
                }
            } catch (MessagingException e) {
                System.err.println("Error closing store: " + e.getMessage());
            }
        }
    }
}
