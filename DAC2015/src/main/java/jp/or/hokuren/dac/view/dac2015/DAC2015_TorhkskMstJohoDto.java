/*
 * ClassName   DAC2015_TorhkskMstJohoDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     取引先マスタ情報Dto
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
 * 取引先マスタ情報Dto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2015_TorhkskMstJohoDto {

  /** 取引先コード */
  private String torhkskCd;

  /** 取引先略名漢字 */
  private String torhkskRyakmeiKj;
}
