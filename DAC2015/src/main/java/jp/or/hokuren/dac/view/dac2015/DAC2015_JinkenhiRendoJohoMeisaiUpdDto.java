/*
 * ClassName   DAC2015_JinkenhiRendoJohoMeisaiUpdDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費連動情報明細 更新Dto
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2015;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 人件費連動情報明細 更新Dto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2015_JinkenhiRendoJohoMeisaiUpdDto {

  /** 最終更新日時 */
  private LocalDateTime lstupdDtm;

  /** 最終更新ユーザＩＤ */
  private String lstupdUserId;

  /** 排他カウンタ */
  private Integer haitaCnt;

  /** 支給日 */
  private LocalDate shikyubi;

  /** 業種コード */
  private String gyoshuCd;

  /** 所属コード */
  private String shozokuCd;

  /** 入力区分 */
  private String nyuryokuKbn;

  /** 給与区分 */
  private String kyuyoKbn;

  /** 給与個所コード */
  private String kyuyoKashoCd;

  /** 人件費科目分類コード */
  private String jinkenhiKamokuBunruiCd;

  /** 人件費科目区分 */
  private String jinkenhiKamokuKbn;

  /** 人件費情報入力項目コード */
  private String jinkenhiJohoNyuryokuKomokuCd;

  /** 金額 */
  private BigDecimal kingaku;

  /** 人件費科目分類補助コード */
  private String jinkenhiKamokuBunruiHojoCd;

  /** 人件費項目区分 */
  private String jinkenhiKomokuKbn;

  /** 会負担保険料区分 */
  private String kaifutanHokenryoKbn;
}
