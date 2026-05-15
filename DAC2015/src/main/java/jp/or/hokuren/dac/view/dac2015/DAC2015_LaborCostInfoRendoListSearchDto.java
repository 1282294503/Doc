/*
 * ClassName   DAC2015_LaborCostInfoRendoListSearchDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報連動一覧 検索条件Dto
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2015;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 人件費情報連動一覧 検索条件Dto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2015_LaborCostInfoRendoListSearchDto {

  /** 基本入力検索条件Dto */
  private DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputSearchDto;

  /** 基本入力選択リスト */
  private List<DAC2015_LaborCostInfoRendoListKeyWordDto> basicInputSelectionList;
}
