/*
 * ClassName   DAC2015_InitMainResponseRecord
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報入力 initMain レスポンスレコード
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
 * 人件費情報入力 initMain レスポンスレコード
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
public record DAC2015_InitMainResponseRecord(
    Integer unyoKanribi,
    List<AWA_ListItem> nyuryokuKbnObjectList,
    List<AWA_ListItem> kyuyoKbnObjectList,
    List<AWA_ListItem> kamokuBunruiObjectList,
    List<AWA_ListItem> kamokuKbnObjectList,
    List<String> operationMessageList) {
}
