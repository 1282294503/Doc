/*
 * ClassName   DAC2015_NextInputMainResponseRecord
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報入力 nextInputMain レスポンスレコード
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2015;

import java.util.List;
import jp.or.hokuren.awa.common.util.AWA_ListItem;

/**
 * <pre>
 * 人件費情報入力 nextInputMain レスポンスレコード
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
public record DAC2015_NextInputMainResponseRecord(
    DAC2015_LaborCostInfoEntryBasicInputDto basicInputDto,
    DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputSearchDto,
    List<AWA_ListItem> basicInputNyuryokuKbnObjectList) {
}
