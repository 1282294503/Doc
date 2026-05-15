/*
 * ClassName   DAC2015_WithdrawInputMainResponseRecord
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報入力 withdrawInputMain レスポンスレコード
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2015;

import java.util.List;
import jp.or.hokuren.aja.view.ajab212.AJAB212_ShoninRouteInputDto;
import jp.or.hokuren.daa.common.utils.DAA_ShinseiShoninJohoDto;

/**
 * <pre>
 * 人件費情報入力 withdrawInputMain レスポンスレコード
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
public record DAC2015_WithdrawInputMainResponseRecord(
    DAC2015_LaborCostInfoEntryBasicInputDto basicInputDto,
    List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> basicInputListDetailDtoList,
    DAA_ShinseiShoninJohoDto shinseiShoninJohoDto,
    AJAB212_ShoninRouteInputDto shoninRouteInputDto) {
}
