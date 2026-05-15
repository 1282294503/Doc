/*
 * ClassName   DAC2015_LaborCostInfoEntryMpr
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報入力 Mapper インタフェース
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
 * 人件費情報入力 Mapper インタフェース
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
public interface DAC2015_LaborCostInfoEntryMpr {

  /**
   * 人件費情報入力一覧検索結果取得
   *
   * @param dto 検索条件Dto
   * @return 人件費情報入力一覧明細Dtoリスト
   */
  List<DAC2015_LaborCostInfoEntryListDetailDto> getSearchResultList(
      DAC2015_LaborCostInfoEntrySearchDto dto);

  /**
   * 人件費情報基本入力一覧取得
   *
   * @param dto 基本入力検索条件Dto
   * @return 人件費情報基本入力一覧明細Dtoリスト
   */
  List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> getLaborCostInfoEntryBasicInputList(
      DAC2015_LaborCostInfoEntryBasicInputSearchDto dto);

  /**
   * 人件費情報基本入力取得
   *
   * @param dto 一覧明細Dto
   * @return 人件費情報基本入力Dto
   */
  DAC2015_LaborCostInfoEntryBasicInputDto getLaborCostInfoEntryBasicInput(
      DAC2015_LaborCostInfoEntryListDetailDto dto);

  /**
   * 人件費連動情報ヘッダ更新
   *
   * @param dto ヘッダDto
   * @return 更新件数
   */
  int updateJinkenhiRendoJohoHeader(DAC2015_JinkenhiRendoJohoHeaderDto dto);

  /**
   * 人件費情報明細入力情報取得
   *
   * @param dto 基本入力一覧明細Dto
   * @return 人件費情報明細入力Dto
   */
  DAC2015_LaborCostInfoEntryDetailInputDto getLaborCostDetailInfo(
      DAC2015_LaborCostInfoEntryBasicInputListDetailDto dto);

  /**
   * 人件費情報連動一覧取得
   *
   * @param dto 連動一覧検索条件Dto
   * @return 人件費情報連動一覧CSV出力Dtoリスト
   */
  List<DAC2015_LaborCostInfoRendoListCsvDto> getLaborCostInfoRendoList(
      DAC2015_LaborCostInfoRendoListSearchDto dto);

  /**
   * 取引先マスタ情報取得
   *
   * @param dto 取引先マスタ検索Dto
   * @return 取引先マスタ情報Dto
   */
  DAC2015_TorhkskMstJohoDto getTorhkskMstInfo(DAC2015_TorhkskMstSearchDto dto);
}
