/*
 * ClassName   DAC2015_DeleteBasicInputMainResponseRecord
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報基本入力 deleteBasicInputMain レスポンスレコード
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2015;

import jp.or.hokuren.aja.view.ajab212.AJAB212_ShoninRouteInputDto;

/**
 * <pre>
 * 人件費情報基本入力 deleteBasicInputMain レスポンスレコード
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
public record DAC2015_DeleteBasicInputMainResponseRecord(
    DAC2015_LaborCostInfoEntryDetailInputDto detailInputDto,
    AJAB212_ShoninRouteInputDto shoninRouteInputDto) {
}
