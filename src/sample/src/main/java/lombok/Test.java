package lombok;

import java.io.Serializable;
import java.util.Date;

@Data
@With
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Test implements Serializable {

    private static final long serialVersionUID = -4591360551571189284L;

    private Long id;
    private String nome;
    private Date date;
    private Boolean active;
}
