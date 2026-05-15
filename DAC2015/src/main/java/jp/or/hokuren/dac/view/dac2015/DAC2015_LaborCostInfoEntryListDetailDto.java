/*
 * ClassName   DAC2015_LaborCostInfoEntryListDetailDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報入力 一覧明細Dto
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
 * 人件費情報入力 一覧明細Dto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2015_LaborCostInfoEntryListDetailDto {

  /** 選択ＩＤ */
  private Integer selectionId;

  /** 承認状況区分 */
  private String shoninJokyoKbn;

  /** 承認状況 */
  private String shoninJokyo;

  /** 更新日 */
  private LocalDate koshinbi;

  /** 経理日 */
  private LocalDate keiribi;

  /** 更新者 */
  private String koshinsha;

  /** 支給日 */
  private LocalDate shikyubi;

  /** 入力区分コード */
  private String nyuryokuKbnCd;

  /** 入力区分名称 */
  private String nyuryokuKbnMeisho;

  /** 連動データ総件数 */
  private Integer rendoDataSokensu;

  /** 修正総件数 */
  private Integer shuseiSokensu;

  /** 伝票番号 */
  private String dempyoNo;

  /** 申請番号 */
  private String shinseiNo;

  /** 排他カウンタ */
  private Integer haitaCnt;

  /** 伝票番号（給与控除） */
  private String dempyoNoKyuyoKojo;

  /** 申請番号（給与控除） */
  private String shinseiNoKyuyoKojo;

  /** 排他カウンタ（給与控除） */
  private Integer haitaCntKyuyoKojo;
}
