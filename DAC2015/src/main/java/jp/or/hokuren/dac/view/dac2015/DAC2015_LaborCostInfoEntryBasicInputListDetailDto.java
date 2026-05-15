/*
 * ClassName   DAC2015_LaborCostInfoEntryBasicInputListDetailDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報基本入力 一覧明細Dto
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2015;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 人件費情報基本入力 一覧明細Dto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2015_LaborCostInfoEntryBasicInputListDetailDto {

  /** 選択ＩＤ */
  private Integer selectionId;

  /** 支給日 */
  private LocalDate shikyubi;

  /** 入力区分コード */
  private String nyuryokuKbnCd;

  /** 入力区分名称 */
  private String nyuryokuKbnMeisho;

  /** 給与区分コード */
  private String kyuyoKbnCd;

  /** 給与区分名称 */
  private String kyuyoKbnMeisho;

  /** 給与個所コード */
  private String kyuyoKashoCd;

  /** 給与個所名称 */
  private String kyuyoKashoMeisho;

  /** 科目分類コード */
  private String kamokuBunruiCd;

  /** 科目分類名称 */
  private String kamokuBunruiMeisho;

  /** 科目区分コード */
  private String kamokuKbnCd;

  /** 科目区分名称 */
  private String kamokuKbnMeisho;

  /** 業種コード */
  private String gyoshuCd;

  /** 業種名称 */
  private String gyoshuMeisho;

  /** 所属コード */
  private String shozokuCd;

  /** 所属名称 */
  private String shozokuMeisho;

  /** 更新者 */
  private String koshinsha;

  /** 更新日 */
  private LocalDate koshinbi;

  /** 支払先区分 */
  private String shiharaisakiKbn;

  /** 支払先コード */
  private String shiharaisakiCd;

  /** 支払先名称 */
  private String shiharaisakiMeisho;

  /** 支払予定日 */
  private String shiharaiYoteibi;
}
