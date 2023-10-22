package entity;

import javax.annotation.processing.Generated;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "TODO")
public class Todo {

    @Id
    @GeneratedValue(strategy = .IDENTITY)
    private Long id;

    @Column(...)
    String name;
    String description;
    LocalDate dueDate;
}
