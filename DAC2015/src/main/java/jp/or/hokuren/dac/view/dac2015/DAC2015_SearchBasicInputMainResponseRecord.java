/*
 * ClassName   DAC2015_SearchBasicInputMainResponseRecord
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報基本入力 searchBasicInputMain レスポンスレコード
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2015;

import java.util.List;

/**
 * <pre>
 * 人件費情報基本入力 searchBasicInputMain レスポンスレコード
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
public record DAC2015_SearchBasicInputMainResponseRecord(
    List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> basicInputListDetailDtoList,
    String kyuyoKashoMeisho,
    String gyoshuMeisho,
    String shozokuMeisho) {
}
