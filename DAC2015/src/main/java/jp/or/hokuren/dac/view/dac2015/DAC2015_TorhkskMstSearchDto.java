/*
 * ClassName   DAC2015_TorhkskMstSearchDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     取引先マスタ 検索Dto
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
 * 取引先マスタ 検索Dto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2015_TorhkskMstSearchDto {

  /** 取引先コード */
  private String torhkskCd;

  /** 対象日付 */
  private Integer taishoDate;
}
