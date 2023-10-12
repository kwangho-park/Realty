package kr.com.pkh.batch.extend.job.DbDataReadWrite.accounts;

import kr.com.pkh.batch.extend.job.DbDataReadWrite.orders.OrderEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Data
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String orderItem;
    private Integer price;
    private Date orderDate;
    private Date accountDate;

    public AccountEntity(OrderEntity orderEntity){
        this.id = orderEntity.getId();
        this.orderItem = orderEntity.getOrderItem();
        this.price = orderEntity.getPrice();
        this.orderDate = orderEntity.getOrderDate();
        this.accountDate = new Date();
    }
}
