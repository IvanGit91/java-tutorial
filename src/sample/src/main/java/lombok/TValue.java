package lombok;

@Data
@Value //IMMUTABLE
@EqualsAndHashCode
@ToString
public class TValue {
    private Long id = 1L;
    private String nome = "dsa";
}
