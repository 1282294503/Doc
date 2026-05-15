/*
 * ClassName   DAC2015_ModifyBasicInputMainResponseRecord
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報基本入力 modifyBasicInputMain レスポンスレコード
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2015;

import jp.or.hokuren.aja.view.ajab212.AJAB212_ShoninRouteInputDto;
import jp.or.hokuren.daa.common.utils.DAA_ShinseiShoninJohoDto;

/**
 * <pre>
 * 人件費情報基本入力 modifyBasicInputMain レスポンスレコード
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
public record DAC2015_ModifyBasicInputMainResponseRecord(
    DAC2015_LaborCostInfoEntryDetailInputDto detailInputDto,
    DAA_ShinseiShoninJohoDto shinseiShoninJohoDto,
    AJAB212_ShoninRouteInputDto shoninRouteInputDto) {
}
