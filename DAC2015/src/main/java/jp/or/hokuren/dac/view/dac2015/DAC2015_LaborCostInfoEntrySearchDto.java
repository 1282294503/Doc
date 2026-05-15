/*
 * ClassName   DAC2015_LaborCostInfoEntrySearchDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報入力 検索条件Dto
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
 * 人件費情報入力 検索条件Dto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2015_LaborCostInfoEntrySearchDto {

  /** 対象年月（From） */
  private Integer taishoNengetsuFrom;

  /** 対象年月（To） */
  private Integer taishoNengetsuTo;

  /** 承認状況（未修正） */
  private boolean shoninJokyoMishusei;

  /** 承認状況（差戻） */
  private boolean shoninJokyoSashimodoshi;

  /** 承認状況（下書保存） */
  private boolean shoninJokyoShitagakiHozon;

  /** 承認状況（承認依頼中） */
  private boolean shoninJokyoShoninIraichu;

  /** 承認状況（承認済） */
  private boolean shoninJokyoShoninzumi;
}
