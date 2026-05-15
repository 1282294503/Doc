/*
 * ClassName   DAC2015_LaborCostInfoEntryBasicInputSearchDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報基本入力 検索条件Dto
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
 * 人件費情報基本入力 検索条件Dto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2015_LaborCostInfoEntryBasicInputSearchDto {

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

  /** 一覧明細入力区分コード */
  private String listDetailNyuryokuKbnCd;
}
