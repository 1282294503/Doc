/*
 * ClassName   DAC2024_OutputFeeAllocationStatementMpr
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     手数料配分計算書出力 Mapper
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2024;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 手数料配分計算書出力 Mapper
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
public interface DAC2024_OutputFeeAllocationStatementMpr {

  /**
   * 選択可能業種コードリスト取得
   *
   * @param params 検索パラメータ（組織コード、基準日）
   * @return 選択可能業種コードリスト
   */
  List<String> getSelectGyoshuList(Map<String, Object> params);

  /**
   * 全道共計手数料配分計算データ一覧取得
   *
   * @param searchDto 検索条件Dto
   * @return 全道共計手数料配分計算書CSV出力Dtoリスト
   */
  List<DAC2024_ZendoKyokeiFeeAllocationStatementCsvDto> getZendoKyokeiFeeAllocationStatementList(
      DAC2024_OutputFeeAllocationStatementSearchDto searchDto);

  /**
   * 購買手数料配分計算データ一覧取得
   *
   * @param searchDto 検索条件Dto
   * @return 購買手数料配分計算書CSV出力Dtoリスト
   */
  List<DAC2024_PurchaseFeeAllocationStatementCsvDto> getPurchaseFeeAllocationStatementList(
      DAC2024_OutputFeeAllocationStatementSearchDto searchDto);
}
