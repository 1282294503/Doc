/*
 * ClassName   DAC2015_LaborCostInfoEntryDao
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報入力 Dao
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2015;

import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import jp.or.hokuren.aza.common.def.AZA_MessageNoCns;
import jp.or.hokuren.aza.database.dao.AZA_CustomDaoUtil;
import jp.or.hokuren.aza.exception.AZA_ResourceBusyException;

/**
 * <pre>
 * 人件費情報入力 Dao
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@RequestScoped
public class DAC2015_LaborCostInfoEntryDao {

  /** SQLセッション */
  @Inject
  private SqlSession sqlSession;

  /** 人件費情報入力 Mapper */
  private DAC2015_LaborCostInfoEntryMpr mapper;

  /**
   * 初期化処理
   */
  @PostConstruct
  public void init() {
    mapper = sqlSession.getMapper(DAC2015_LaborCostInfoEntryMpr.class);
  }

  /**
   * 人件費情報入力一覧検索結果取得
   *
   * @param dto 検索条件Dto
   * @return 人件費情報入力一覧明細Dtoリスト
   * @throws AZA_ResourceBusyException リソースビジー例外
   */
  public List<DAC2015_LaborCostInfoEntryListDetailDto> getSearchResultList(
      DAC2015_LaborCostInfoEntrySearchDto dto) throws AZA_ResourceBusyException {

    return AZA_CustomDaoUtil.select(() -> mapper.getSearchResultList(dto),
        AZA_MessageNoCns.AZA00004);
  }

  /**
   * 人件費情報基本入力一覧取得
   *
   * @param dto 基本入力検索条件Dto
   * @return 人件費情報基本入力一覧明細Dtoリスト
   * @throws AZA_ResourceBusyException リソースビジー例外
   */
  public List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> getLaborCostInfoEntryBasicInputList(
      DAC2015_LaborCostInfoEntryBasicInputSearchDto dto) throws AZA_ResourceBusyException {

    return AZA_CustomDaoUtil.select(() -> mapper.getLaborCostInfoEntryBasicInputList(dto),
        AZA_MessageNoCns.AZA00004);
  }

  /**
   * 人件費情報基本入力取得
   *
   * @param dto 一覧明細Dto
   * @return 人件費情報基本入力Dto
   * @throws AZA_ResourceBusyException リソースビジー例外
   */
  public DAC2015_LaborCostInfoEntryBasicInputDto getLaborCostInfoEntryBasicInput(
      DAC2015_LaborCostInfoEntryListDetailDto dto) throws AZA_ResourceBusyException {

    return AZA_CustomDaoUtil.select(() -> mapper.getLaborCostInfoEntryBasicInput(dto),
        AZA_MessageNoCns.AZA00004);
  }

  /**
   * 人件費連動情報ヘッダ更新
   *
   * @param dto ヘッダDto
   * @return 更新件数
   * @throws AZA_ResourceBusyException リソースビジー例外
   */
  public int updateJinkenhiRendoJohoHeader(DAC2015_JinkenhiRendoJohoHeaderDto dto)
      throws AZA_ResourceBusyException {

    return AZA_CustomDaoUtil.update(() -> mapper.updateJinkenhiRendoJohoHeader(dto),
        AZA_MessageNoCns.AZA00004);
  }

  /**
   * 人件費情報明細入力情報取得
   *
   * @param dto 基本入力一覧明細Dto
   * @return 人件費情報明細入力Dto
   * @throws AZA_ResourceBusyException リソースビジー例外
   */
  public DAC2015_LaborCostInfoEntryDetailInputDto getLaborCostDetailInfo(
      DAC2015_LaborCostInfoEntryBasicInputListDetailDto dto) throws AZA_ResourceBusyException {

    return AZA_CustomDaoUtil.select(() -> mapper.getLaborCostDetailInfo(dto),
        AZA_MessageNoCns.AZA00004);
  }

  /**
   * 人件費情報連動一覧取得
   *
   * @param dto 連動一覧検索条件Dto
   * @return 人件費情報連動一覧CSV出力Dtoリスト
   * @throws AZA_ResourceBusyException リソースビジー例外
   */
  public List<DAC2015_LaborCostInfoRendoListCsvDto> getLaborCostInfoRendoList(
      DAC2015_LaborCostInfoRendoListSearchDto dto) throws AZA_ResourceBusyException {

    return AZA_CustomDaoUtil.select(() -> mapper.getLaborCostInfoRendoList(dto),
        AZA_MessageNoCns.AZA00004);
  }

  /**
   * 取引先マスタ情報取得
   *
   * @param dto 取引先マスタ検索Dto
   * @return 取引先マスタ情報Dto
   * @throws AZA_ResourceBusyException リソースビジー例外
   */
  public DAC2015_TorhkskMstJohoDto getTorhkskMstInfo(
      DAC2015_TorhkskMstSearchDto dto) throws AZA_ResourceBusyException {

    return AZA_CustomDaoUtil.select(() -> mapper.getTorhkskMstInfo(dto),
        AZA_MessageNoCns.AZA00004);
  }
}
