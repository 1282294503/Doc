/*
 * ClassName   DAC2015_LaborCostInfoRendoListKeyWordDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報連動一覧 キーワードDto
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2015;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 人件費情報連動一覧 キーワードDto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2015_LaborCostInfoRendoListKeyWordDto {

  /** 入力区分コード */
  private String nyuryokuKbnCd;

  /** 給与区分コード */
  private String kyuyoKbnCd;

  /** 給与個所コード */
  private String kyuyoKashoCd;

  /** 科目分類コード */
  private String kamokuBunruiCd;

  /** 科目区分コード */
  private String kamokuKbnCd;

  /** 業種コード */
  private String gyoshuCd;

  /** 所属コード */
  private String shozokuCd;
}
