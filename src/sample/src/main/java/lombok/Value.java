package lombok;

@Data
@lombok.Value //IMMUTABLE
@EqualsAndHashCode
@ToString
public class Value {
    private Long id = 1L;
    private String nome = "dsa";
}
