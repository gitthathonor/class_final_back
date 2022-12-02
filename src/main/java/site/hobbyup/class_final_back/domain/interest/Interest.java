package site.hobbyup.class_final_back.domain.interest;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.hobbyup.class_final_back.domain.category.Category;
import site.hobbyup.class_final_back.domain.user.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "interest")
@Entity
public class Interest {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @ManyToOne(fetch = FetchType.LAZY)
  private Category category;

  @Builder
  public Interest(Long id, User user, Category category) {
    this.id = id;
    this.user = user;
    this.category = category;
  }

}
