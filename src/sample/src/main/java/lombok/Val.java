package lombok;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Val {
    private Long id = 1L;
    @val
    private String nome = "dsa";
}
