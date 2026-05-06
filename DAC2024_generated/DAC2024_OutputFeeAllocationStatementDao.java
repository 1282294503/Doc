/*
 * ClassName   DAC2024_OutputFeeAllocationStatementDao
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     手数料配分計算書出力 Dao
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2024;

import java.util.List;
import java.util.Map;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import jp.or.hokuren.aza.common.def.AZA_MessageNoCns;
import jp.or.hokuren.aza.database.dao.AZA_CustomDaoUtil;
import jp.or.hokuren.aza.exception.AZA_ResourceBusyException;

/**
 * <pre>
 * 手数料配分計算書出力 Dao
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@RequestScoped
public class DAC2024_OutputFeeAllocationStatementDao {

  /** SQLセッション */
  @Inject
  private SqlSession sqlSession;

  /** 手数料配分計算書出力 Mapper */
  private DAC2024_OutputFeeAllocationStatementMpr mapper;

  /**
   * 初期化処理
   */
  @PostConstruct
  public void init() {
    mapper = sqlSession.getMapper(DAC2024_OutputFeeAllocationStatementMpr.class);
  }

  /**
   * 選択可能業種コードリスト取得
   *
   * @param params 検索パラメータ（組織コード、基準日）
   * @return 選択可能業種コードリスト
   * @throws AZA_ResourceBusyException リソースビジー例外
   */
  public List<String> getSelectGyoshuList(Map<String, Object> params)
      throws AZA_ResourceBusyException {

    return AZA_CustomDaoUtil.select(() -> mapper.getSelectGyoshuList(params),
        AZA_MessageNoCns.AZA00004);
  }

  /**
   * 全道共計手数料配分計算データ一覧取得
   *
   * @param searchDto 検索条件Dto
   * @return 全道共計手数料配分計算書CSV出力Dtoリスト
   * @throws AZA_ResourceBusyException リソースビジー例外
   */
  public List<DAC2024_ZendoKyokeiFeeAllocationStatementCsvDto> getZendoKyokeiFeeAllocationStatementList(
      DAC2024_OutputFeeAllocationStatementSearchDto searchDto)
      throws AZA_ResourceBusyException {

    return AZA_CustomDaoUtil.select(
        () -> mapper.getZendoKyokeiFeeAllocationStatementList(searchDto),
        AZA_MessageNoCns.AZA00004);
  }

  /**
   * 購買手数料配分計算データ一覧取得
   *
   * @param searchDto 検索条件Dto
   * @return 購買手数料配分計算書CSV出力Dtoリスト
   * @throws AZA_ResourceBusyException リソースビジー例外
   */
  public List<DAC2024_PurchaseFeeAllocationStatementCsvDto> getPurchaseFeeAllocationStatementList(
      DAC2024_OutputFeeAllocationStatementSearchDto searchDto)
      throws AZA_ResourceBusyException {

    return AZA_CustomDaoUtil.select(
        () -> mapper.getPurchaseFeeAllocationStatementList(searchDto),
        AZA_MessageNoCns.AZA00004);
  }
}
