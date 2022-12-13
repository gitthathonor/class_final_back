package site.hobbyup.class_final_back.domain.lesson;

// interface Dao {
//   List<LessonSortListRespDto> findAllByCategoryWithSort(Long userId, Long categoryId, String sorting);
// }

public class LessonRepositoryImpl {

  // @Override
  // public List<LessonSortListRespDto> findAllByCategoryWithSort(Long userId,
  // Long categoryId, String sorting) {
  // String sql = "";
  // sql += "select t from Transaction t ";
  // sql += "left join t.withdrawAccount wa ";
  // sql += "left join t.depositAccount da ";

  // TypedQuery<Transaction> query = em.createQuery(sql, Transaction.class);

  // if (gubun == null || gubun.isEmpty()) {
  // query = query.setParameter("withdrawAccountId", accountId);
  // query = query.setParameter("depositAccountId", accountId);
  // } else if (TransactionEnum.valueOf(gubun) == TransactionEnum.DEPOSIT) {
  // query = query.setParameter("depositAccountId", accountId);
  // } else if (TransactionEnum.valueOf(gubun) == TransactionEnum.WITHDRAW) {
  // query = query.setParameter("withdrawAccountId", accountId);
  // }

  // query.setFirstResult(page * 3);
  // query.setMaxResults(3);
  // return query.getResultList();
  // return null;
  // }

}
