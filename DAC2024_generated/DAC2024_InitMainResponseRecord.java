/*
 * ClassName   DAC2024_InitMainResponseRecord
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     手数料配分計算書出力 initMain レスポンスレコード
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2024;

import java.time.LocalDate;

/**
 * <pre>
 * 手数料配分計算書出力 initMain レスポンスレコード
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
public record DAC2024_InitMainResponseRecord(
    DAC2024_OutputFeeAllocationStatementSearchDto searchConditionDto,
    LocalDate unyoKanribi,
    String exportOperationMessage,
    String sanshoKengen,
    String shozokuCd,
    Boolean industryLabelDisplayFlag) {
}
