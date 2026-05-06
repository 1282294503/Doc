/*
 * ClassName   DAC2024_OutputFeeAllocationStatementLgc
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     手数料配分計算書出力 ロジック
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2024;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.transaction.Transactional.TxType;
import jakarta.transaction.TransactionAttribute;
import jakarta.transaction.TransactionAttributeType;
import jp.or.hokuren.aza.exception.AZA_ApplicationException;
import jp.or.hokuren.aza.session.AZA_LogonInfoDto;
import jp.or.hokuren.daa.common.object.DAA_GyoshuJohoDto;
import jp.or.hokuren.daa.common.object.DAA_MasterData;
import jp.or.hokuren.daa.common.utils.DAA_DateUtils;
import jp.or.hokuren.daa.common.utils.DAA_FileUtils;
import jp.or.hokuren.daa.common.utils.DAA_MessageUtils;
import jp.or.hokuren.daa.def.DAA_CommonsCns;
import jp.or.hokuren.daa.def.DAC_MessageNoCns;
import jp.scsk.arvicio3.log.annotation.AZA_LogicLogInterceptor;
import jp.scsk.arvicio3.mybatis.transaction.TransactionLogic;
import org.primefaces.model.StreamedContent;

/**
 * <pre>
 * 手数料配分計算書出力 ロジック
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@RequestScoped
public class DAC2024_OutputFeeAllocationStatementLgc {

  // インジェクションクラス
  /** ログオン情報 */
  @Inject
  private AZA_LogonInfoDto logonInfo;

  /** ファイルUtils */
  @Inject
  private DAA_FileUtils fileUtils;

  /** マスタデータ */
  @Inject
  private DAA_MasterData masterDataUtil;

  /** 日時Utils */
  @Inject
  private DAA_DateUtils dateUtils;

  /** メッセージUtils */
  @Inject
  private DAA_MessageUtils messageUtils;

  /** 手数料配分計算書出力 DAO */
  @Inject
  private DAC2024_OutputFeeAllocationStatementDao dao;

  /**
   * <pre>
   * 手数料配分計算書出力 初期処理メイン
   * 初期表示時に実行する処理を実施する。
   * </pre>
   *
   * @param searchConditionDto 検索条件Dto
   * @return 初期処理レスポンスレコード
   * @throws AZA_ApplicationException 業務例外
   */
  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public DAC2024_InitMainResponseRecord initMain(
      DAC2024_OutputFeeAllocationStatementSearchDto searchConditionDto)
      throws AZA_ApplicationException {

    // 1. 日時Utils#運用管理日取得を実行して、ローカル変数.運用管理日に設定する。
    LocalDate unyoKanribi = dateUtils.getUnyoKanriDate();

    // 2. メッセージUtils#メッセージ取得を実行して、操作メッセージに設定する。
    String exportOperationMessage =
        messageUtils.getMessage(DAC_MessageNoCns.DAC00316I, null, null, null);

    // 3. ログオン情報.選択所属コードを取得する。
    String selectSyozokuCd = logonInfo.getSelectSyozokCd();

    // 4. 参照権限区分を判定する。
    // 選択所属コードが 主計課コード or 財務課コード or 経理課コード or 審査課コード の場合、経理部とする。
    String sanshoKengen;
    if (DAA_CommonsCns.SHUKEIKA_CD.equals(selectSyozokuCd)
        || DAA_CommonsCns.DAA_SHOZOKU_ZAIMU.equals(selectSyozokuCd)
        || DAA_CommonsCns.DAA_SHOZOKU_KEIRI.equals(selectSyozokuCd)
        || DAC2024_LocalCns.SHINSAKA_CD.equals(selectSyozokuCd)) {
      // 経理部の場合
      sanshoKengen = DAC2024_LocalCns.SANSHO_KENGEN_KEIRIBU;
    } else {
      // 経理部以外の場合
      sanshoKengen = DAC2024_LocalCns.SANSHO_KENGEN_NOT_KEIRIBU;
    }

    // 5. 選択可能業種コードリストを取得する。
    List<String> selectableGyoshuCdList = new ArrayList<>();
    if (DAC2024_LocalCns.SANSHO_KENGEN_NOT_KEIRIBU.equals(sanshoKengen)) {
      // 経理部以外の場合は、DAO#選択可能業種コードリスト取得を実行する。
      Map<String, Object> params = new HashMap<>();
      params.put("shozokuCd", selectSyozokuCd);
      params.put("kijunbi", dateUtils.convertLocalDateToInteger(unyoKanribi));
      selectableGyoshuCdList = dao.getSelectGyoshuList(params);
    }

    // 6. 業種ラベル表示フラグを設定する。
    // 選択可能業種コードリストが1件の場合はtrue、それ以外はfalseとする。
    Boolean industryLabelDisplayFlag = (selectableGyoshuCdList.size() == 1);

    // 7. 検索条件Dtoの初期値を設定する。
    DAC2024_OutputFeeAllocationStatementSearchDto initSearchConditionDto =
        new DAC2024_OutputFeeAllocationStatementSearchDto();
    initSearchConditionDto.setSanshoKengen(sanshoKengen);
    initSearchConditionDto.setShozokuCd(selectSyozokuCd);
    initSearchConditionDto.setSelectableGyoshuCdList(selectableGyoshuCdList);
    initSearchConditionDto.setOutputChohyoId(
        DAC2024_LocalCns.OUTPUT_CHOHYO_ID_ZENDO_KYOKEI_TESURYO_HAIBUN_KEISANSHO);

    // 7-1. 選択可能業種コードリストが1件の場合は業種名称を設定する。
    if (industryLabelDisplayFlag) {
      String gyoshuCd = selectableGyoshuCdList.get(0);
      DAA_GyoshuJohoDto gyoshuJohoDto = masterDataUtil.getGyoshuJoho(gyoshuCd, unyoKanribi);
      if (gyoshuJohoDto != null) {
        initSearchConditionDto.setGyoshuCd(gyoshuCd);
        initSearchConditionDto.setGyoshuMei(gyoshuJohoDto.getGyoshuMeisho());
      }
    }

    return new DAC2024_InitMainResponseRecord(
        initSearchConditionDto,
        unyoKanribi,
        exportOperationMessage,
        sanshoKengen,
        selectSyozokuCd,
        industryLabelDisplayFlag);
  }

  /**
   * <pre>
   * 手数料配分計算書出力 表示処理メイン
   * 業種名称の表示を更新する。
   * </pre>
   *
   * @param searchConditionDto 検索条件Dto
   * @param unyoKanribi 運用管理日
   * @return 表示処理レスポンスレコード
   * @throws AZA_ApplicationException 業務例外
   */
  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public DAC2024_ShowMainResponseRecord showMain(
      DAC2024_OutputFeeAllocationStatementSearchDto searchConditionDto,
      LocalDate unyoKanribi)
      throws AZA_ApplicationException {

    // 1. 表示名称取得を実行する。
    DAC2024_GetDispNameResponseRecord dispNameRecord =
        getDispName(searchConditionDto, unyoKanribi);

    return new DAC2024_ShowMainResponseRecord(dispNameRecord.gyoshuMei());
  }

  /**
   * <pre>
   * 手数料配分計算書出力 CSV出力メイン
   * 検索条件に基づき CSV ファイルを出力する。
   * </pre>
   *
   * @param searchConditionDto 検索条件Dto
   * @param unyoKanribi 運用管理日
   * @return StreamedContent CSVファイル
   * @throws AZA_ApplicationException 業務例外
   */
  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public StreamedContent exportCsvMain(
      DAC2024_OutputFeeAllocationStatementSearchDto searchConditionDto,
      LocalDate unyoKanribi)
      throws AZA_ApplicationException {

    // 1. 単項目チェックを実行する。
    validateSingle(searchConditionDto, unyoKanribi);

    // 2. 関連チェックを実行する。
    validateRelated(searchConditionDto, unyoKanribi);

    // 3. 出力帳票IDに応じて CSV データを取得し出力する。
    String outputChohyoId = searchConditionDto.getOutputChohyoId();

    if (DAC2024_LocalCns.OUTPUT_CHOHYO_ID_ZENDO_KYOKEI_TESURYO_HAIBUN_KEISANSHO
        .equals(outputChohyoId)) {
      // 全道共計手数料配分計算書の場合
      List<DAC2024_ZendoKyokeiFeeAllocationStatementCsvDto> csvList =
          searchZendoKyokeiFeeAllocationStatement(searchConditionDto);
      return fileUtils.downloadCsv(
          DAC2024_LocalCns.CSV_REPORT_ID_ZENDO_KYOKEI_TESURYO_HAIBUN_KEISANSHO,
          csvList,
          true);
    } else {
      // 購買手数料配分計算書の場合
      List<DAC2024_PurchaseFeeAllocationStatementCsvDto> csvList =
          searchPurchaseFeeAllocationStatement(searchConditionDto);
      return fileUtils.downloadCsv(
          DAC2024_LocalCns.CSV_REPORT_ID_KOBAI_TESURYO_HAIBUN_KEISANSHO,
          csvList,
          true);
    }
  }

  /**
   * 単項目チェック
   *
   * @param searchConditionDto 検索条件Dto
   * @param unyoKanribi 運用管理日
   * @throws AZA_ApplicationException 業務例外
   */
  private void validateSingle(
      DAC2024_OutputFeeAllocationStatementSearchDto searchConditionDto,
      LocalDate unyoKanribi)
      throws AZA_ApplicationException {
    // 設計書に基づく単項目チェック処理を実装する。
  }

  /**
   * 関連チェック
   *
   * @param searchConditionDto 検索条件Dto
   * @param unyoKanribi 運用管理日
   * @throws AZA_ApplicationException 業務例外
   */
  private void validateRelated(
      DAC2024_OutputFeeAllocationStatementSearchDto searchConditionDto,
      LocalDate unyoKanribi)
      throws AZA_ApplicationException {
    // 設計書に基づく関連チェック処理を実装する。
  }

  /**
   * 表示名称取得
   *
   * @param searchConditionDto 検索条件Dto
   * @param unyoKanribi 運用管理日
   * @return 表示名称レスポンスレコード
   * @throws AZA_ApplicationException 業務例外
   */
  private DAC2024_GetDispNameResponseRecord getDispName(
      DAC2024_OutputFeeAllocationStatementSearchDto searchConditionDto,
      LocalDate unyoKanribi)
      throws AZA_ApplicationException {

    String gyoshuMei = null;

    // 業種コードが設定されている場合、マスタデータから業種名称を取得する。
    if (searchConditionDto.getGyoshuCd() != null
        && !searchConditionDto.getGyoshuCd().isEmpty()) {
      DAA_GyoshuJohoDto gyoshuJohoDto =
          masterDataUtil.getGyoshuJoho(searchConditionDto.getGyoshuCd(), unyoKanribi);
      if (gyoshuJohoDto != null) {
        gyoshuMei = gyoshuJohoDto.getGyoshuMeisho();
      }
    }

    return new DAC2024_GetDispNameResponseRecord(gyoshuMei);
  }

  /**
   * 全道共計手数料配分計算書データ検索
   *
   * @param searchConditionDto 検索条件Dto
   * @return 全道共計手数料配分計算書CSV出力Dtoリスト
   * @throws AZA_ApplicationException 業務例外
   */
  private List<DAC2024_ZendoKyokeiFeeAllocationStatementCsvDto> searchZendoKyokeiFeeAllocationStatement(
      DAC2024_OutputFeeAllocationStatementSearchDto searchConditionDto)
      throws AZA_ApplicationException {

    // DAO#全道共計手数料配分計算データ一覧取得を実行する。
    return dao.getZendoKyokeiFeeAllocationStatementList(searchConditionDto);
  }

  /**
   * 購買手数料配分計算書データ検索
   *
   * @param searchConditionDto 検索条件Dto
   * @return 購買手数料配分計算書CSV出力Dtoリスト
   * @throws AZA_ApplicationException 業務例外
   */
  private List<DAC2024_PurchaseFeeAllocationStatementCsvDto> searchPurchaseFeeAllocationStatement(
      DAC2024_OutputFeeAllocationStatementSearchDto searchConditionDto)
      throws AZA_ApplicationException {

    // DAO#購買手数料配分計算データ一覧取得を実行する。
    return dao.getPurchaseFeeAllocationStatementList(searchConditionDto);
  }
}
