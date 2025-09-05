package jdbcEx03.vo;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@ToString
public class Account {
    private String ano;
    private String owner;
    private BigDecimal balance;
}
