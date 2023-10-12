package kr.com.pkh.batch.extend.job.DbDataReadWrite.orders;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", length=11)
    private Integer id;

    @Column(name="order_item", length = 45)
    private String orderItem;

    @Column(name="price")
    private Integer price;

    @Column(name="order_date")
    private Date orderDate;

}
