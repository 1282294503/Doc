/*
 * ClassName   DAC2015_GetConfirmInfoResponseRecord
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報確認 getConfirmInfo レスポンスレコード
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
 * 人件費情報確認 getConfirmInfo レスポンスレコード
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
public record DAC2015_GetConfirmInfoResponseRecord(
    List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> basicInputListDetailDtoList,
    DAA_ShinseiShoninJohoDto shinseiShoninJohoDto,
    AJAB212_ShoninRouteInputDto shoninRouteInputDto) {
}
