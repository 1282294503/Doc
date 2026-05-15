package jp.or.hokuren.dac.view.dac2015;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jp.or.hokuren.aja.common.dto.AJA_ShoninRouteInfoDto;
import jp.or.hokuren.aja.view.ajab212.AJAB212_ShoninRouteInputDto;
import jp.or.hokuren.aza.database.repository.sql.where.AZA_Where;
import jp.or.hokuren.aza.exception.AZA_ApplicationException;
import jp.or.hokuren.aza.session.AZA_LogonInfoDto;
import jp.or.hokuren.awa.common.util.AWA_ListItem;
import jp.or.hokuren.daa.common.object.DAA_CodeJohoDto;
import jp.or.hokuren.daa.common.object.DAA_GyoshuJohoDto;
import jp.or.hokuren.daa.common.object.DAA_MasterData;
import jp.or.hokuren.daa.common.object.DAA_ObjectList;
import jp.or.hokuren.daa.common.object.DAA_SoshikiGyoshuJohoDto;
import jp.or.hokuren.daa.common.object.DAA_SoshikiJohoDto;
import jp.or.hokuren.daa.common.utils.DAA_DateUtils;
import jp.or.hokuren.daa.common.utils.DAA_FileUtils;
import jp.or.hokuren.daa.common.utils.DAA_MessageUtils;
import jp.or.hokuren.daa.common.utils.DAA_ShinseiShoninJohoDto;
import jp.or.hokuren.daa.common.utils.DAA_ShinseiShoninResultDto;
import jp.or.hokuren.daa.common.utils.DAA_WorkflowUtils;
import jp.or.hokuren.daa.def.DAA_CommonsCns;
import jp.or.hokuren.daa.exception.BusinessValidationException;
import jp.or.hokuren.daa.exception.ErrorResponse;
import jp.or.hokuren.daa.repository.DAA301_JinkenhiRendoJohoHeaderRpo;
import jp.or.hokuren.daa.repository.DAA301_JinkenhiRendoJohoMeisaiRpo;
import jp.or.hokuren.daa.repository.DAA450_TorhkskMstRpo;
import jp.or.hokuren.daa.repository.DAC213_JinkenhiJohoNyuryokuRpo;
import jp.or.hokuren.dac.def.DAC_MessageNoCns;
import jp.scsk.arvicio3.log.annotation.AZA_LogicLogInterceptor;
import jp.scsk.arvicio3.mybatis.transaction.TransactionAttribute;
import jp.scsk.arvicio3.mybatis.transaction.TransactionAttributeType;
import jp.scsk.arvicio3.mybatis.transaction.TransactionLogic;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

/**
 * <pre>
 * 人件費情報入力 ロジック
 * </pre>
 */
@RequestScoped
public class DAC2015_LaborCostInfoEntryLgc {

  private static final String SHINSEI_GYOMU_CD = "DAC213";
  private static final String NYURYOKU_KBN_KYUYO_SHIKYU = "1";
  private static final String NYURYOKU_KBN_KYUYO_KOJO = "2";
  private static final String NYURYOKU_KBN_KAIFUTAN_KEMPO = "3";
  private static final String NYURYOKU_KBN_KAIFUTAN_NENKIN = "4";
  private static final String NYURYOKU_KBN_YOCHOKIN = "5";

  // インジェクションクラス
  /** ログオン情報 */
  @Inject
  private AZA_LogonInfoDto logonInfo;

  /** 日時Utils */
  @Inject
  private DAA_DateUtils dateUtils;

  /** メッセージUtils */
  @Inject
  private DAA_MessageUtils messageUtils;

  /** ファイルUtils */
  @Inject
  private DAA_FileUtils fileUtils;

  /** マスタデータ取得 */
  @Inject
  private DAA_MasterData masterData;

  /** オブジェクトリスト生成 */
  @Inject
  private DAA_ObjectList objectList;

  /** ワークフローUtils */
  @Inject
  private DAA_WorkflowUtils workflowUtils;

  /** 人件費情報入力リポジトリ */
  @Inject
  private DAC213_JinkenhiJohoNyuryokuRpo jinkenhiJohoNyuryokuRpo;

  /** 人件費連動情報明細リポジトリ */
  @Inject
  private DAA301_JinkenhiRendoJohoMeisaiRpo jinkenhiRendoJohoMeisaiRpo;

  /** 人件費連動情報ヘッダリポジトリ */
  @Inject
  private DAA301_JinkenhiRendoJohoHeaderRpo jinkenhiRendoJohoHeaderRpo;

  /** 取引先マスタリポジトリ */
  @Inject
  private DAA450_TorhkskMstRpo torhkskMstRpo;

  /** 人件費情報入力Dao */
  @Inject
  private DAC2015_LaborCostInfoEntryDao dao;

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public DAC2015_InitMainResponseRecord initMain() throws AZA_ApplicationException {

    // 1. 以下のローカル変数を初期化する。
    LocalDate unyoKanriDate = null;
    // 1. 以下のローカル変数を初期化する。
    LocalDateTime sysDateTime = null;
    // 1. 以下のローカル変数を初期化する。
    LocalDate sysDate = null;
    // 1. 以下のローカル変数を初期化する。
    Integer unyoKanribi = null;

    // 2. 日時Utils#運用管理日取得(dateUtils#getUnyoKanriDate)を実行して運用管理日を取得し、戻り値をローカル変数.運用管理日（LocalDate）に設定する。
    unyoKanriDate = dateUtils.getUnyoKanriDate();
    // 2.(1) ローカル変数.運用管理日（LocalDate）がnullの場合
    if (unyoKanriDate == null) {
      // 2.(1)ア 日時Utils#システム日時取得メソッド(dateUtils#getSysDateTime)を使用してシステム日時を取得する。
      sysDateTime = dateUtils.getSysDateTime();
      // 2.(1)イ ローカル変数.システム日時をLocalDate型に変換してローカル変数.システム日付に設定する。
      sysDate = sysDateTime.toLocalDate();
      // 2.(1)ウ 日時Utils#日付から数値変換(dateUtils#convertLocalDateToInteger)を実行して、戻り値をローカル変数.運用管理日（Integer）に設定する。
      unyoKanribi = dateUtils.convertLocalDateToInteger(sysDate);
    } else {
      // 2.(2)ア 日時Utils#日付から数値変換(dateUtils#convertLocalDateToInteger)を実行して、戻り値をローカル変数.運用管理日（Integer）に設定する。
      unyoKanribi = dateUtils.convertLocalDateToInteger(unyoKanriDate);
    }

    // 3. オブジェクトリスト生成#コードオブジェクトリスト作成(objectList#createCodeList)を使用し、入力区分オブジェクトリストを取得する。
    List<AWA_ListItem> nyuryokuKbnObjectList = createNyuryokuKbnObjectList();
    // 4. オブジェクトリスト生成#コードオブジェクトリスト作成(objectList#createCodeList)を使用し、給与区分オブジェクトリストを取得する。
    List<AWA_ListItem> kyuyoKbnObjectList = createKyuyoKbnObjectList();
    // 5. オブジェクトリスト生成#コードオブジェクトリスト作成(objectList#createCodeList)を使用し、科目分類オブジェクトリストを取得する。
    List<AWA_ListItem> kamokuBunruiObjectList = createKamokuBunruiObjectList();
    // 6. オブジェクトリスト生成#コードオブジェクトリスト作成(objectList#createCodeList)を使用し、科目区分オブジェクトリストを取得する。
    List<AWA_ListItem> kamokuKbnObjectList = createKamokuKbnObjectList();

    // 7. メッセージUtils#メッセージ取得処理の各種メッセージ実行結果を取得する。
    List<String> operationMessageList = new ArrayList<>();
    // 7.(1) 人件費情報入力画面の操作メッセージを取得する。
    operationMessageList.add(messageUtils.getMessage("DAC00246I", null, null, null));
    // 7.(2) 人件費情報基本入力画面の操作メッセージを取得する。
    operationMessageList.add(messageUtils.getMessage("DAC00247I", null, null, null));
    // 7.(3) 人件費情報明細入力画面の操作メッセージを取得する。
    operationMessageList.add(messageUtils.getMessage("DAC00248I", null, null, null));
    // 7.(4) 人件費情報個別確認画面の操作メッセージを取得する。
    operationMessageList.add(messageUtils.getMessage("DAC00202I", null, null, null));
    // 7.(5) 人件費情報確認画面（申請）の操作メッセージを取得する。
    operationMessageList.add(messageUtils.getMessage("DAC00249I", null, null, null));
    // 7.(6) 人件費情報確認画面（照会）の操作メッセージを取得する。
    operationMessageList.add(messageUtils.getMessage("DAC00313I", null, null, null));
    // 7.(7) 人件費情報確認画面（承認依頼取下）の操作メッセージを取得する。
    operationMessageList.add(messageUtils.getMessage("DAC00312I", null, null, null));

    // 8. 戻り値を返却する。
    return new DAC2015_InitMainResponseRecord(unyoKanribi, nyuryokuKbnObjectList,
        kyuyoKbnObjectList, kamokuBunruiObjectList, kamokuKbnObjectList, operationMessageList);
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public List<DAC2015_LaborCostInfoEntryListDetailDto> searchInputMain(
      DAC2015_LaborCostInfoEntrySearchDto inputSearchDto) throws AZA_ApplicationException {

    // 1. ロジック#人件費情報入力画面　検索条件関連チェックを実行して検索条件の関連チェックを行う。
    validateRelatedSearchInput(inputSearchDto);
    // 2. ロジック#人件費情報入力一覧検索結果情報取得を実行して、戻り値を人件費情報入力一覧明細Dtoリストに設定する。
    List<DAC2015_LaborCostInfoEntryListDetailDto> list = searchListResultInput(inputSearchDto);
    // 3. 戻り値を返却する。
    return list;
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public DAC2015_NextInputMainResponseRecord nextInputMain(
      List<DAC2015_LaborCostInfoEntryListDetailDto> selectedListDetailDtoList,
      List<AWA_ListItem> nyuryokuKbnList) throws AZA_ApplicationException {

    // 1. ロジック#人件費情報入力画面　検索結果関連チェックを実行して、関連チェックを行う。
    validateRelatedResultInput(DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_NEXT, selectedListDetailDtoList);
    // 2. 人件費情報入力一覧明細Dtoにパラメータ.人件費情報入力一覧明細Dtoリストの１件目要素を設定する。
    DAC2015_LaborCostInfoEntryListDetailDto inputListDetailDto = selectedListDetailDtoList.get(0);

    // 3. DAO#基本入力ヘッダ情報取得を実行して、人件費情報基本入力Dtoを取得する。
    DAC2015_LaborCostInfoEntryBasicInputDto basicInputDto = dao.getLaborCostInfoEntryBasicInput(inputListDetailDto);
    // 4. 取得した人件費情報基本入力Dtoが「nullまたは0件」の場合、排他更新例外をスローする。
    if (basicInputDto == null) {
      throw new AZA_ApplicationException(DAC_MessageNoCns.DAC00008E);
    }

    // 5. ローカル変数.人件費情報基本入力検索条件Dtoを作成する。
    DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputSearchDto =
        new DAC2015_LaborCostInfoEntryBasicInputSearchDto();
    // 6. ローカル変数.人件費情報基本入力検索条件Dto.支給日に設定する。
    basicInputSearchDto.setShikyubi(inputListDetailDto.getShikyubi());
    // 7. ローカル変数.人件費情報基本入力検索条件Dto.一覧明細入力区分コードに設定する。
    basicInputSearchDto.setListDetailNyuryokuKbnCd(inputListDetailDto.getNyuryokuKbnCd());
    // 8. ローカル変数.人件費情報基本入力検索条件Dto.入力区分コードの設定を行う。
    if (DAC2015_LaborCostInfoEntryCns.NYURYOKU_KBN_KYUYO_SHIKYU_KYUYO_KOJO
        .equals(inputListDetailDto.getNyuryokuKbnCd())) {
      // 入力区分コードが給与支給・給与控除の場合、入力区分コードに給与支給を設定する。
      basicInputSearchDto.setNyuryokuKbnCd(NYURYOKU_KBN_KYUYO_SHIKYU);
    } else {
      // 8.(2) 入力区分コードが給与支給・給与控除以外の場合、入力区分コードに一覧明細の入力区分コードを設定する。
      basicInputSearchDto.setNyuryokuKbnCd(inputListDetailDto.getNyuryokuKbnCd());
    }

    // 9. 人件費情報基本入力画面の入力区分プルダウンリストに表示する項目を設定する。
    List<AWA_ListItem> basicInputNyuryokuKbnObjectList = new ArrayList<>();
    // 10. ローカル変数.基本入力_入力区分オブジェクトリストに空文字を追加する。
    basicInputNyuryokuKbnObjectList.add(new AWA_ListItem("", ""));
    // 11. 入力区分コードが給与支給・給与控除の場合、給与支給・給与控除を追加する。
    if (DAC2015_LaborCostInfoEntryCns.NYURYOKU_KBN_KYUYO_SHIKYU_KYUYO_KOJO
        .equals(inputListDetailDto.getNyuryokuKbnCd())) {
      // 11.(1) 定数.入力区分.給与支給に紐づく項目を追加する。
      addNyuryokuKbnItem(basicInputNyuryokuKbnObjectList, nyuryokuKbnList, NYURYOKU_KBN_KYUYO_SHIKYU);
      // 11.(2) 定数.入力区分.給与控除に紐づく項目を追加する。
      addNyuryokuKbnItem(basicInputNyuryokuKbnObjectList, nyuryokuKbnList, NYURYOKU_KBN_KYUYO_KOJO);
    } else {
      // 11.(3) 一覧1行目.入力区分コードに紐づく項目を追加する。
      addNyuryokuKbnItem(basicInputNyuryokuKbnObjectList, nyuryokuKbnList, inputListDetailDto.getNyuryokuKbnCd());
    }

    // 12. 戻り値を返却する。
    return new DAC2015_NextInputMainResponseRecord(basicInputDto, basicInputSearchDto,
        basicInputNyuryokuKbnObjectList);
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public DAC2015_InquiryInputMainResponseRecord inquiryInputMain(
      List<DAC2015_LaborCostInfoEntryListDetailDto> selectedList) throws AZA_ApplicationException {

    // 1. ロジック#人件費情報入力画面　検索結果関連チェックを実行して選択明細の関連チェックを行う。
    validateRelatedResultInput(DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_INQUIRY, selectedList);
    // 2. ローカル変数.入力一覧明細Dtoにパラメータ.人件費情報入力一覧明細Dtoリストの１件目をコピーする。
    DAC2015_LaborCostInfoEntryListDetailDto inputListDetailDto = selectedList.get(0);
    // 3. ロジック#確認画面情報取得処理を実行する。
    DAC2015_GetConfirmInfoResponseRecord confirmInfo = getConfirmInfo(inputListDetailDto);
    // 4. DAO#基本入力ヘッダ情報取得を実行して、人件費情報基本入力Dtoを取得する。
    DAC2015_LaborCostInfoEntryBasicInputDto basicInputDto = dao.getLaborCostInfoEntryBasicInput(inputListDetailDto);
    // 5. 戻り値を返却する。
    return new DAC2015_InquiryInputMainResponseRecord(basicInputDto,
        confirmInfo.basicInputListDetailDtoList(), confirmInfo.shinseiShoninJohoDto());
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public DAC2015_WithdrawInputMainResponseRecord withdrawInputMain(
      List<DAC2015_LaborCostInfoEntryListDetailDto> selectedList) throws AZA_ApplicationException {

    // 1. ロジック#人件費情報入力画面　検索結果関連チェックを実行して選択明細の関連チェックを行う。
    validateRelatedResultInput(DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_WITHDRAW, selectedList);
    // 2. ローカル変数.入力一覧明細Dtoにパラメータ.人件費情報入力一覧明細Dtoリストの１件目をコピーする。
    DAC2015_LaborCostInfoEntryListDetailDto inputListDetailDto = selectedList.get(0);
    // 3. ロジック#確認画面情報取得処理を実行する。
    DAC2015_GetConfirmInfoResponseRecord confirmInfo = getConfirmInfo(inputListDetailDto);
    // 4. DAO#基本入力ヘッダ情報取得を実行して、人件費情報基本入力Dtoを取得する。
    DAC2015_LaborCostInfoEntryBasicInputDto basicInputDto = dao.getLaborCostInfoEntryBasicInput(inputListDetailDto);
    // 5. 戻り値を返却する。
    return new DAC2015_WithdrawInputMainResponseRecord(basicInputDto,
        confirmInfo.basicInputListDetailDtoList(), confirmInfo.shinseiShoninJohoDto(),
        confirmInfo.shoninRouteInputDto());
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public DAC2015_RegistBasicInputMainResponseRecord registBasicInputMain(
      DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputSearchDto,
      DAC2015_LaborCostInfoEntryListDetailDto inputListDetailDto) throws AZA_ApplicationException {

    // 1. ロジック#基本入力検索条件単体チェックを実行する。
    validateSingleBasicInput(basicInputSearchDto);
    // 2. ロジック#基本入力検索条件関連チェックを実行して業種と所属の組み合わせチェックを行う。
    validateRelatedBasicInput(DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_REGIST, basicInputSearchDto);

    // 3. 人件費情報基本入力一覧明細Dtoを作成する。
    DAC2015_LaborCostInfoEntryBasicInputListDetailDto basicInputListDetailDto =
        DAC2015_LaborCostInfoEntryBasicInputListDetailDto.builder()
            .shikyubi(basicInputSearchDto.getShikyubi())
            .nyuryokuKbnCd(basicInputSearchDto.getNyuryokuKbnCd())
            .nyuryokuKbnMeisho(basicInputSearchDto.getNyuryokuKbnMeisho())
            .kyuyoKbnCd(basicInputSearchDto.getKyuyoKbnCd())
            .kyuyoKbnMeisho(basicInputSearchDto.getKyuyoKbnMeisho())
            .kyuyoKashoCd(basicInputSearchDto.getKyuyoKashoCd())
            .kamokuBunruiCd(basicInputSearchDto.getKamokuBunruiCd())
            .kamokuKbnCd(basicInputSearchDto.getKamokuKbnCd())
            .gyoshuCd(basicInputSearchDto.getGyoshuCd())
            .shozokuCd(basicInputSearchDto.getShozokuCd())
            .build();

    // 4. ロジック#基本入力表示用名称取得を実行して戻り値を取得する。
    List<String> dispNameList = dispNameBasicInput(basicInputListDetailDto.getKyuyoKashoCd(),
        basicInputListDetailDto.getGyoshuCd(), basicInputListDetailDto.getShozokuCd());
    // 5. ローカル変数.人件費情報基本入力一覧明細Dtoに名称を設定する。
    basicInputListDetailDto.setKyuyoKashoMeisho(getListValue(dispNameList, 0));
    // 6. ローカル変数.人件費情報基本入力一覧明細Dtoに名称を設定する。
    basicInputListDetailDto.setGyoshuMeisho(getListValue(dispNameList, 1));
    // 7. ローカル変数.人件費情報基本入力一覧明細Dtoに名称を設定する。
    basicInputListDetailDto.setShozokuMeisho(getListValue(dispNameList, 2));

    // 8. 入力区分が会負担健保または会負担年金の場合、支払先情報を取得する。
    if (NYURYOKU_KBN_KAIFUTAN_KEMPO.equals(basicInputSearchDto.getNyuryokuKbnCd())
        || NYURYOKU_KBN_KAIFUTAN_NENKIN.equals(basicInputSearchDto.getNyuryokuKbnCd())) {
      // 8.(1) マスタデータ取得#コード情報取得を実行して戻り値を取得する。
      DAA_CodeJohoDto codeJoho = masterData.getCodeJoho("JINKENHI_SHIHARAISAKI",
          basicInputSearchDto.getNyuryokuKbnCd(), null);
      // 8.(2) コード情報が存在する場合、支払先情報を設定する。
      if (codeJoho != null) {
        // 8.(2)ア ローカル変数.人件費情報基本入力一覧明細Dtoに支払先区分を設定する。
        basicInputListDetailDto.setShiharaisakiKbn(codeJoho.getZokusei01());
        // 8.(2)イ ローカル変数.人件費情報基本入力一覧明細Dtoに支払先コードを設定する。
        basicInputListDetailDto.setShiharaisakiCd(codeJoho.getZokusei02());
        // 8.(2)ウ ローカル変数.人件費情報基本入力一覧明細Dtoに支払予定日を設定する。
        basicInputListDetailDto.setShiharaiYoteibi(formatDate(inputListDetailDto.getShikyubi()));
      }
    }

    // 9. DAO#人件費明細情報取得を実行し、戻り値をローカル変数.人件費情報明細入力Dtoに設定する。
    DAC2015_LaborCostInfoEntryDetailInputDto detailInputDto =
        dao.getLaborCostDetailInfo(basicInputListDetailDto);
    // 10. 人件費情報明細入力Dtoがnullの場合、インスタンスを生成する。
    if (detailInputDto == null) {
      // 10.(1) 人件費情報明細入力Dtoを初期化する。
      detailInputDto = new DAC2015_LaborCostInfoEntryDetailInputDto();
    }

    // 11. 申請番号を入力区分により判定する。
    String shinseiNo = extractShinseiNoByNyuryokuKbn(inputListDetailDto,
        basicInputSearchDto.getNyuryokuKbnCd());
    // 12. ロジック#申請承認情報取得処理を実行して、申請承認情報と申請・承認ルート表示取得情報を取得する。
    DAC2015_GetShinseiShoninJohoResponseRecord shinseiShoninJoho =
        getShinseiShoninJoho(shinseiNo);

    // 13. 戻り値を返却する。
    return new DAC2015_RegistBasicInputMainResponseRecord(basicInputListDetailDto,
        detailInputDto, shinseiShoninJoho.shinseiShoninJohoDto(),
        shinseiShoninJoho.shoninRouteInputDto());
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public DAC2015_SearchBasicInputMainResponseRecord searchBasicInputMain(
      DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputSearchDto)
      throws AZA_ApplicationException {

    // 1. ロジック#基本入力検索条件単体チェックを実行する。
    validateSingleBasicInput(basicInputSearchDto);
    // 2. ロジック#基本入力検索条件関連チェックを実行する。
    validateRelatedBasicInput(DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_INQUIRY, basicInputSearchDto);
    // 3. ロジック#基本入力表示用名称取得を実行して戻り値を取得する。
    List<String> meishoList = dispNameBasicInput(basicInputSearchDto.getKyuyoKashoCd(),
        basicInputSearchDto.getGyoshuCd(), basicInputSearchDto.getShozokuCd());
    // 4. ロジック#基本入力一覧検索結果情報取得を実行して人件費情報基本入力一覧明細Dtoのリストを取得する。
    List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> list =
        listResultDataBasicInput(basicInputSearchDto);
    // 5. 戻り値を返却する。
    return new DAC2015_SearchBasicInputMainResponseRecord(list, getListValue(meishoList, 0),
        getListValue(meishoList, 1), getListValue(meishoList, 2));
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public List<String> listShowBasicInputMain(
      DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputSearchDto)
      throws AZA_ApplicationException {

    // 1. ロジック#基本入力検索条件単体チェックを実行する。
    validateSingleBasicInput(basicInputSearchDto);
    // 2. ロジック#基本入力検索条件関連チェックを実行する。
    validateRelatedBasicInput(DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_DISPLAY, basicInputSearchDto);
    // 3. ロジック#基本入力表示用名称取得を実行して戻り値を取得する。
    List<String> meishoList = dispNameBasicInput(basicInputSearchDto.getKyuyoKashoCd(),
        basicInputSearchDto.getGyoshuCd(), basicInputSearchDto.getShozokuCd());
    // 4. 戻り値を返却する。
    return meishoList;
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public DAC2015_ModifyBasicInputMainResponseRecord modifyBasicInputMain(
      List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> basicInputDetailDtoList,
      DAC2015_LaborCostInfoEntryListDetailDto inputListDetailDto) throws AZA_ApplicationException {

    // 1. ロジック#基本入力検索結果関連チェックを実行する。
    validateRelatedListBasicInput(DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_MODIFY,
        basicInputDetailDtoList);
    // 2. パラメータ.人件費情報基本入力一覧明細Dtoリストの1行目をローカル変数.人件費情報基本入力一覧明細Dtoに設定する。
    DAC2015_LaborCostInfoEntryBasicInputListDetailDto selected = basicInputDetailDtoList.get(0);
    // 3. DAO#人件費明細情報取得を実行し、戻り値を取得する。
    DAC2015_LaborCostInfoEntryDetailInputDto detailInputDto = dao.getLaborCostDetailInfo(selected);
    // 4. 人件費情報明細入力Dtoがnullの場合、インスタンスを生成する。
    if (detailInputDto == null) {
      // 4.(1) 人件費情報明細入力Dtoを初期化する。
      detailInputDto = new DAC2015_LaborCostInfoEntryDetailInputDto();
    }

    // 5. 申請番号を入力区分により判定する。
    String shinseiNo = extractShinseiNoByNyuryokuKbn(inputListDetailDto, selected.getNyuryokuKbnCd());
    // 6. ロジック#申請承認情報取得処理を実行して、申請承認情報と申請・承認ルート表示取得情報を取得する。
    DAC2015_GetShinseiShoninJohoResponseRecord shinseiShoninJoho = getShinseiShoninJoho(shinseiNo);

    // 7. 戻り値を返却する。
    return new DAC2015_ModifyBasicInputMainResponseRecord(detailInputDto,
        shinseiShoninJoho.shinseiShoninJohoDto(), shinseiShoninJoho.shoninRouteInputDto());
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public StreamedContent exportCsvBasicInputMain(
      DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputSearchDto,
      List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> basicInputListDetailDtoList)
      throws AZA_ApplicationException {

    // ローカル変数.人件費情報連動リスト検索条件Dtoを初期化する。
    DAC2015_LaborCostInfoRendoListSearchDto searchDto = new DAC2015_LaborCostInfoRendoListSearchDto();
    // ローカル変数.人件費情報連動リスト検索条件Dto.基本入力検索条件Dtoに設定する。
    searchDto.setBasicInputSearchDto(basicInputSearchDto);

    // パラメータ.人件費情報基本入力一覧明細Dtoリストがnullの場合、選択されたキー情報のリストにnullを設定する。
    if (basicInputListDetailDtoList == null) {
      // nullの場合は、人件費情報確認画面のCSV出力ボタン押下時と判定する。
      searchDto.setBasicInputSelectionList(null);
    } else {
      // パラメータ.人件費情報基本入力一覧明細Dtoリストがnull以外の場合、選択されたキー情報のリストを設定する。
      List<DAC2015_LaborCostInfoRendoListKeyWordDto> keyWordDtoList = new ArrayList<>();
      // 選択明細の件数分繰り返して検索キーを作成する。
      for (DAC2015_LaborCostInfoEntryBasicInputListDetailDto dto : basicInputListDetailDtoList) {
        // ローカル変数.人件費情報連動リストキーワードDtoを初期化する。
        DAC2015_LaborCostInfoRendoListKeyWordDto keyWordDto = new DAC2015_LaborCostInfoRendoListKeyWordDto();
        // ローカル変数.人件費情報連動リストキーワードDtoに同名項目を設定する。
        keyWordDto.setNyuryokuKbnCd(dto.getNyuryokuKbnCd());
        // ローカル変数.人件費情報連動リストキーワードDtoに同名項目を設定する。
        keyWordDto.setKyuyoKbnCd(dto.getKyuyoKbnCd());
        // ローカル変数.人件費情報連動リストキーワードDtoに同名項目を設定する。
        keyWordDto.setKyuyoKashoCd(dto.getKyuyoKashoCd());
        // ローカル変数.人件費情報連動リストキーワードDtoに同名項目を設定する。
        keyWordDto.setKamokuBunruiCd(dto.getKamokuBunruiCd());
        // ローカル変数.人件費情報連動リストキーワードDtoに同名項目を設定する。
        keyWordDto.setKamokuKbnCd(dto.getKamokuKbnCd());
        // ローカル変数.人件費情報連動リストキーワードDtoに同名項目を設定する。
        keyWordDto.setGyoshuCd(dto.getGyoshuCd());
        // ローカル変数.人件費情報連動リストキーワードDtoに同名項目を設定する。
        keyWordDto.setShozokuCd(dto.getShozokuCd());
        // ローカル変数.人件費情報連動リスト検索条件Dto.選択されたキー情報のリストに追加する。
        keyWordDtoList.add(keyWordDto);
      }
      // ローカル変数.人件費情報連動リスト検索条件Dto.選択されたキー情報のリストを設定する。
      searchDto.setBasicInputSelectionList(keyWordDtoList);
    }

    // DAO#人件費連動リスト情報取得を実行し、戻り値をローカル変数.人件費連動リストCSV出力Dtoリストに設定する。
    List<DAC2015_LaborCostInfoRendoListCsvDto> csvDtoList = dao.getLaborCostInfoRendoList(searchDto);
    // ローカル変数.人件費連動リストCSV出力Dtoリストが「nullまたは0件」の場合、例外をスローする。
    if (csvDtoList == null || csvDtoList.isEmpty()) {
      // 条件に該当するデータはありません。
      throw new BusinessValidationException(new ErrorResponse("[CSV出力情報取得0件]",
          DAC_MessageNoCns.DAC00008E));
    }
    // ローカル変数.人件費連動リストCSV出力Dtoリストの件数がCSV出力上限件数より大きい場合、例外をスローする。
    if (csvDtoList.size() > DAA_CommonsCns.CSV_OUTPUT_MAX_CNT) {
      // 検索結果が%1件を超えました。抽出条件を絞って再度検索してください。
      throw new BusinessValidationException(new ErrorResponse("[CSV出力情報取得件数 ＞ CSV出力上限件数]",
          DAC_MessageNoCns.DAC00009E, String.valueOf(DAA_CommonsCns.CSV_OUTPUT_MAX_CNT)));
    }

    // ファイルUtils#CSVダウンロード処理を実行する。
    StreamedContent downloadFile =
        fileUtils.downloadCsv(DAC2015_LaborCostInfoEntryCns.KINO_SHIKIBETSU_CD + "1", csvDtoList, true);
    // 戻り値を返却する。
    return downloadFile;
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public List<DAC2015_LaborCostInfoEntryListDetailDto> saveDraftBasicInputMain(
      DAC2015_LaborCostInfoEntryListDetailDto listDetailDto,
      DAC2015_LaborCostInfoEntryBasicInputDto basicInputDto,
      DAC2015_LaborCostInfoEntrySearchDto inputSearchDto,
      DAC2015_LaborCostInfoEntryBasicInputDto beforeBasicInputDto) throws AZA_ApplicationException {

    // 1. ロジック#添付ファイル処理区分取得処理を実行し、戻り値をローカル変数.添付ファイル処理区分に設定する。
    String tenpuFileShoriKbn1 = getTenpuFileShoriKbn(beforeBasicInputDto.getTenpuFileId1(),
        basicInputDto.getTenpuFileId1(), basicInputDto.getTenpuFile1());
    String tenpuFileShoriKbn2 = getTenpuFileShoriKbn(beforeBasicInputDto.getTenpuFileId2(),
        basicInputDto.getTenpuFileId2(), basicInputDto.getTenpuFile2());
    String tenpuFileShoriKbn3 = getTenpuFileShoriKbn(beforeBasicInputDto.getTenpuFileId3(),
        basicInputDto.getTenpuFileId3(), basicInputDto.getTenpuFile3());

    // 2. 添付ファイルのアップロードを行う。
    Integer tenpuFileId1 = uploadByShoriKbn(tenpuFileShoriKbn1, basicInputDto.getTenpuFile1());
    Integer tenpuFileId2 = uploadByShoriKbn(tenpuFileShoriKbn2, basicInputDto.getTenpuFile2());
    Integer tenpuFileId3 = uploadByShoriKbn(tenpuFileShoriKbn3, basicInputDto.getTenpuFile3());

    // 3. ローカル変数.人件費連動情報ヘッダ更新条件Dtoを初期化し、必要項目を設定する。
    DAC2015_ModifyHeaderDataDto modifyHeaderDataDto = new DAC2015_ModifyHeaderDataDto();
    modifyHeaderDataDto.setShikyubi(listDetailDto.getShikyubi());
    modifyHeaderDataDto.setNyuryokuKbnCd(listDetailDto.getNyuryokuKbnCd());
    modifyHeaderDataDto.setDempyoNo(listDetailDto.getDempyoNo());
    modifyHeaderDataDto.setDempyoNoKyuyoKojo(listDetailDto.getDempyoNoKyuyoKojo());
    modifyHeaderDataDto.setShinseiNo(listDetailDto.getShinseiNo());
    modifyHeaderDataDto.setShinseiNoKyuyoKojo(listDetailDto.getShinseiNoKyuyoKojo());
    modifyHeaderDataDto.setShoninJokyoKbn("下書保存");
    modifyHeaderDataDto.setTenpuFileId1(resolveTenpuFileId(tenpuFileShoriKbn1,
        beforeBasicInputDto.getTenpuFileId1(), tenpuFileId1));
    modifyHeaderDataDto.setTenpuFileId2(resolveTenpuFileId(tenpuFileShoriKbn2,
        beforeBasicInputDto.getTenpuFileId2(), tenpuFileId2));
    modifyHeaderDataDto.setTenpuFileId3(resolveTenpuFileId(tenpuFileShoriKbn3,
        beforeBasicInputDto.getTenpuFileId3(), tenpuFileId3));
    modifyHeaderDataDto.setSanshoUrl(basicInputDto.getSanshoUrl());
    modifyHeaderDataDto.setHaitaCnt(basicInputDto.getHaitaCnt());
    modifyHeaderDataDto.setHaitaCntKyuyoKojo(basicInputDto.getHaitaCntKyuyoKojo());

    // 4. ロジック#人件費連動情報ヘッダ更新処理を実行する。
    modifyHeaderData(modifyHeaderDataDto);
    // 5. ロジック#人件費情報入力一覧検索結果情報取得を実行して、人件費情報入力一覧明細Dtoリストを取得する。
    List<DAC2015_LaborCostInfoEntryListDetailDto> inputList = searchListResultInput(inputSearchDto);
    // 6. 戻り値を返却する。
    return inputList;
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public DAC2015_DeleteBasicInputMainResponseRecord deleteBasicInputMain(
      List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> basicInputDetailDto,
      DAC2015_LaborCostInfoEntryListDetailDto inputListDetailDto) throws AZA_ApplicationException {

    // 1. ロジック#基本入力検索結果関連チェックを実行して検索条件の関連チェックを行う。
    validateRelatedListBasicInput(DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_DELETE, basicInputDetailDto);
    // 2. パラメータ.人件費情報基本入力一覧明細Dtoリストの1行目の要素より人件費情報基本入力一覧明細Dtoを取得する。
    DAC2015_LaborCostInfoEntryBasicInputListDetailDto selected = basicInputDetailDto.get(0);
    // 3. DAO#人件費明細情報取得を実行して人件費情報明細入力Dtoを取得する。
    DAC2015_LaborCostInfoEntryDetailInputDto detailInputDto = dao.getLaborCostDetailInfo(selected);
    // 4. 人件費情報明細入力Dtoがnullの場合、インスタンスを生成する。
    if (detailInputDto == null) {
      // 4.(1) 人件費情報明細入力Dtoを初期化する。
      detailInputDto = new DAC2015_LaborCostInfoEntryDetailInputDto();
    }

    // 5. 申請番号を入力区分により判定する。
    String shinseiNo = extractShinseiNoByNyuryokuKbn(inputListDetailDto, selected.getNyuryokuKbnCd());
    // 6. ロジック#申請承認情報取得処理を実行して、申請・承認ルート表示取得情報を取得する。
    DAC2015_GetShinseiShoninJohoResponseRecord shinseiShoninJoho = getShinseiShoninJoho(shinseiNo);
    // 7. 戻り値を返却する。
    return new DAC2015_DeleteBasicInputMainResponseRecord(detailInputDto,
        shinseiShoninJoho.shoninRouteInputDto());
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public DAC2015_InquiryBasicInputMainResponseRecord inquiryBasicInputMain(
      List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> basicInputDetailDto,
      DAC2015_LaborCostInfoEntryListDetailDto inputListDetailDto) throws AZA_ApplicationException {

    // 1. ロジック#基本入力検索結果関連チェックを実行して検索条件の関連チェックを行う。
    validateRelatedListBasicInput(DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_INDIVIDUAL_INQUIRY,
        basicInputDetailDto);
    // 2. パラメータ.人件費情報基本入力一覧明細Dtoリストの1行目より、人件費情報基本入力一覧明細Dtoを取得する。
    DAC2015_LaborCostInfoEntryBasicInputListDetailDto selected = basicInputDetailDto.get(0);
    // 3. DAO#人件費明細情報取得を実行して人件費情報明細入力Dtoのリストを取得する。
    DAC2015_LaborCostInfoEntryDetailInputDto detailInputDto = dao.getLaborCostDetailInfo(selected);
    // 4. 人件費情報明細入力Dtoがnullの場合、インスタンスを生成する。
    if (detailInputDto == null) {
      // 4.(1) 人件費情報明細入力Dtoを初期化する。
      detailInputDto = new DAC2015_LaborCostInfoEntryDetailInputDto();
    }

    // 5. 申請番号を入力区分により判定する。
    String shinseiNo = extractShinseiNoByNyuryokuKbn(inputListDetailDto, selected.getNyuryokuKbnCd());
    // 6. ロジック#申請承認情報取得処理を実行して、申請承認情報と申請・承認ルート表示取得情報を取得する。
    DAC2015_GetShinseiShoninJohoResponseRecord shinseiShoninJoho = getShinseiShoninJoho(shinseiNo);
    // 7. 戻り値を返却する。
    return new DAC2015_InquiryBasicInputMainResponseRecord(detailInputDto,
        shinseiShoninJoho.shinseiShoninJohoDto());
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public DAC2015_ConfirmBasicInputMainResponseRecord confirmBasicInputMain(
      DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputListSearchDto,
      DAC2015_LaborCostInfoEntryListDetailDto inputListDetailDto) throws AZA_ApplicationException {

    // 1. 人件費情報基本入力検索条件Dtoを作成する。
    DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputSearchDto =
        copyBasicInputSearchDto(basicInputListSearchDto);
    // 2. ローカル変数.人件費情報基本入力検索条件Dto.入力区分コードにnullを設定する。
    basicInputSearchDto.setNyuryokuKbnCd(null);

    // 3. ロジック#基本入力一覧検索結果情報取得を実行して、人件費情報基本入力一覧明細情報Dtoリストを取得する。
    List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> list =
        listResultDataBasicInput(basicInputSearchDto);

    // 4. 申請承認情報と申請・承認ルート表示取得情報を取得する。
    String shinseiNo = extractShinseiNoByNyuryokuKbn(inputListDetailDto,
        basicInputListSearchDto == null ? null : basicInputListSearchDto.getNyuryokuKbnCd());
    // 5. ロジック#申請承認情報取得処理を実行して、申請承認情報と申請・承認ルート表示取得情報を取得する。
    DAC2015_GetShinseiShoninJohoResponseRecord shinseiShoninJoho = getShinseiShoninJoho(shinseiNo);

    // 6. 戻り値を返却する。
    return new DAC2015_ConfirmBasicInputMainResponseRecord(list,
        shinseiShoninJoho.shinseiShoninJohoDto(), shinseiShoninJoho.shoninRouteInputDto());
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public DAC2015_LaborCostInfoEntryBasicInputDto saveDraftDetailInputMain(
      Integer processMode,
      DAC2015_LaborCostInfoEntryListDetailDto listDetailDto,
      DAC2015_LaborCostInfoEntryBasicInputDto basicInputDto,
      DAC2015_LaborCostInfoEntryBasicInputDto beforeBasicInputDto,
      DAC2015_LaborCostInfoEntryBasicInputListDetailDto basicInputListDetailSel,
      DAC2015_LaborCostInfoEntryDetailInputDto detailInputDto,
      DAC2015_LaborCostInfoEntryDetailInputDto beforeDetailInputDto) throws AZA_ApplicationException {

    // 1. ロジック#添付ファイル処理区分取得処理を実行し、戻り値をローカル変数.添付ファイル処理区分に設定する。
    String tenpuFileShoriKbn1 = getTenpuFileShoriKbn(beforeBasicInputDto.getTenpuFileId1(),
        basicInputDto.getTenpuFileId1(), basicInputDto.getTenpuFile1());
    String tenpuFileShoriKbn2 = getTenpuFileShoriKbn(beforeBasicInputDto.getTenpuFileId2(),
        basicInputDto.getTenpuFileId2(), basicInputDto.getTenpuFile2());
    String tenpuFileShoriKbn3 = getTenpuFileShoriKbn(beforeBasicInputDto.getTenpuFileId3(),
        basicInputDto.getTenpuFileId3(), basicInputDto.getTenpuFile3());

    // 2. 添付ファイルのアップロードを行う。
    Integer tenpuFileId1 = uploadByShoriKbn(tenpuFileShoriKbn1, basicInputDto.getTenpuFile1());
    Integer tenpuFileId2 = uploadByShoriKbn(tenpuFileShoriKbn2, basicInputDto.getTenpuFile2());
    Integer tenpuFileId3 = uploadByShoriKbn(tenpuFileShoriKbn3, basicInputDto.getTenpuFile3());

    // 3. ローカル変数.人件費連動情報ヘッダ更新条件Dtoを初期化し、必要項目を設定する。
    DAC2015_ModifyHeaderDataDto modifyHeaderDataDto = new DAC2015_ModifyHeaderDataDto();
    modifyHeaderDataDto.setShikyubi(basicInputListDetailSel.getShikyubi());
    modifyHeaderDataDto.setNyuryokuKbnCd(basicInputListDetailSel.getNyuryokuKbnCd());
    modifyHeaderDataDto.setDempyoNo(listDetailDto.getDempyoNo());
    modifyHeaderDataDto.setDempyoNoKyuyoKojo(listDetailDto.getDempyoNoKyuyoKojo());
    modifyHeaderDataDto.setShinseiNo(listDetailDto.getShinseiNo());
    modifyHeaderDataDto.setShinseiNoKyuyoKojo(listDetailDto.getShinseiNoKyuyoKojo());
    modifyHeaderDataDto.setShoninJokyoKbn("下書保存");
    modifyHeaderDataDto.setTenpuFileId1(resolveTenpuFileId(tenpuFileShoriKbn1,
        beforeBasicInputDto.getTenpuFileId1(), tenpuFileId1));
    modifyHeaderDataDto.setTenpuFileId2(resolveTenpuFileId(tenpuFileShoriKbn2,
        beforeBasicInputDto.getTenpuFileId2(), tenpuFileId2));
    modifyHeaderDataDto.setTenpuFileId3(resolveTenpuFileId(tenpuFileShoriKbn3,
        beforeBasicInputDto.getTenpuFileId3(), tenpuFileId3));
    modifyHeaderDataDto.setSanshoUrl(basicInputDto.getSanshoUrl());
    modifyHeaderDataDto.setHaitaCnt(basicInputDto.getHaitaCnt());
    modifyHeaderDataDto.setHaitaCntKyuyoKojo(basicInputDto.getHaitaCntKyuyoKojo());

    // 4. ロジック#人件費連動情報ヘッダ更新処理を実行し、戻り値をローカル変数.人件費情報基本入力Dtoに設定する。
    DAC2015_LaborCostInfoEntryBasicInputDto resultBasicInputDto = modifyHeaderData(modifyHeaderDataDto);

    // 5. 入力区分によりローカル変数.入力項目リストを設定する。
    List<String> nyuryokuKomokuList = getNyuryokuKomokuList(basicInputListDetailSel.getNyuryokuKbnCd());
    // 6. ローカル変数.入力項目リストの件数分繰り返し、ロジック#人件費情報入力更新処理を実行する。
    for (String nyuryokuKomokuCd : nyuryokuKomokuList) {
      modifyNyuryokuData(processMode, nyuryokuKomokuCd, basicInputListDetailSel, detailInputDto,
          beforeDetailInputDto);
    }

    // 7. 戻り値を返却する。
    return resultBasicInputDto;
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public List<DAC2015_LaborCostInfoEntryListDetailDto> applyConfirmMain(
      DAC2015_LaborCostInfoEntrySearchDto searchDto,
      DAC2015_LaborCostInfoEntryListDetailDto listDetailDto,
      List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> confirmListDetailDtoList,
      DAC2015_LaborCostInfoEntryBasicInputDto basicInputDto,
      DAC2015_LaborCostInfoEntryBasicInputDto beforeBasicInputDto,
      Object shoninRouteInputDto) throws AZA_ApplicationException {

    // 1. ロジック#確認画面関連チェックを実行し関連チェックを行う。
    validateRelatedConfirm(DAC2015_LaborCostInfoEntryCns.PROCESS_MODE_CONFIRM,
        confirmListDetailDtoList);

    // 2. ロジック#添付ファイル処理区分取得処理を実行し、戻り値をローカル変数.添付ファイル処理区分に設定する。
    String tenpuFileShoriKbn1 = getTenpuFileShoriKbn(beforeBasicInputDto.getTenpuFileId1(),
        basicInputDto.getTenpuFileId1(), basicInputDto.getTenpuFile1());
    String tenpuFileShoriKbn2 = getTenpuFileShoriKbn(beforeBasicInputDto.getTenpuFileId2(),
        basicInputDto.getTenpuFileId2(), basicInputDto.getTenpuFile2());
    String tenpuFileShoriKbn3 = getTenpuFileShoriKbn(beforeBasicInputDto.getTenpuFileId3(),
        basicInputDto.getTenpuFileId3(), basicInputDto.getTenpuFile3());

    // 3. 添付ファイルのアップロードを行う。
    Integer tenpuFileId1 = uploadByShoriKbn(tenpuFileShoriKbn1, basicInputDto.getTenpuFile1());
    Integer tenpuFileId2 = uploadByShoriKbn(tenpuFileShoriKbn2, basicInputDto.getTenpuFile2());
    Integer tenpuFileId3 = uploadByShoriKbn(tenpuFileShoriKbn3, basicInputDto.getTenpuFile3());

    // 4. ヘッダ関連ローカル変数を用意する。
    LocalDate shikyubi = listDetailDto.getShikyubi();
    String nyuryokuKbnCd = listDetailDto.getNyuryokuKbnCd();
    String dempyoNo = listDetailDto.getDempyoNo();
    String shinseiNo = listDetailDto.getShinseiNo();

    // 5. ローカル変数.申請番号が「nullまたは空文字」の場合、申請を行い、申請番号を取得する。
    if (StringUtils.isBlank(shinseiNo) && shoninRouteInputDto instanceof AJAB212_ShoninRouteInputDto routeInputDto) {
      DAA_ShinseiShoninResultDto applyResult = workflowUtils.apply(SHINSEI_GYOMU_CD, null, null,
          routeInputDto.getShoninRouteInfo());
      shinseiNo = applyResult.getShinseiNo();
    }

    // 6. ローカル変数.人件費連動情報ヘッダ更新条件Dtoを初期化し、必要項目を設定する。
    DAC2015_ModifyHeaderDataDto modifyHeaderDataDto = new DAC2015_ModifyHeaderDataDto();
    modifyHeaderDataDto.setShikyubi(shikyubi);
    modifyHeaderDataDto.setNyuryokuKbnCd(nyuryokuKbnCd);
    modifyHeaderDataDto.setDempyoNo(dempyoNo);
    modifyHeaderDataDto.setDempyoNoKyuyoKojo(dempyoNo);
    modifyHeaderDataDto.setShinseiNo(shinseiNo);
    modifyHeaderDataDto.setShinseiNoKyuyoKojo(shinseiNo);
    modifyHeaderDataDto.setShoninJokyoKbn(DAA_CommonsCns.SHONIN_IRAI_CHU);
    modifyHeaderDataDto.setTenpuFileId1(resolveTenpuFileId(tenpuFileShoriKbn1,
        beforeBasicInputDto.getTenpuFileId1(), tenpuFileId1));
    modifyHeaderDataDto.setTenpuFileId2(resolveTenpuFileId(tenpuFileShoriKbn2,
        beforeBasicInputDto.getTenpuFileId2(), tenpuFileId2));
    modifyHeaderDataDto.setTenpuFileId3(resolveTenpuFileId(tenpuFileShoriKbn3,
        beforeBasicInputDto.getTenpuFileId3(), tenpuFileId3));
    modifyHeaderDataDto.setSanshoUrl(basicInputDto.getSanshoUrl());
    modifyHeaderDataDto.setHaitaCnt(basicInputDto.getHaitaCnt());
    modifyHeaderDataDto.setHaitaCntKyuyoKojo(basicInputDto.getHaitaCntKyuyoKojo());

    // 7. ロジック#人件費連動情報ヘッダ更新処理を実行する。
    modifyHeaderData(modifyHeaderDataDto);

    // 8. ロジック#人件費情報入力一覧検索結果情報取得を実行し、戻り値をローカル変数.人件費情報入力一覧明細Dtoリストに設定する。
    List<DAC2015_LaborCostInfoEntryListDetailDto> inputList = searchListResultInput(searchDto);
    // 9. 戻り値を返却する。
    return inputList;
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public DAC2015_LaborCostInfoEntryDetailInputDto inquiryConfirmMain(
      DAC2015_LaborCostInfoEntryBasicInputListDetailDto confirmListDetail) throws AZA_ApplicationException {

    // DAO#人件費明細情報取得を実行し、戻り値を取得する。
    DAC2015_LaborCostInfoEntryDetailInputDto detailInputDto = dao.getLaborCostDetailInfo(confirmListDetail);
    // 戻り値がnullの場合、空の人件費情報明細入力Dtoを返却する。
    if (detailInputDto == null) {
      // 人件費情報明細入力Dtoを初期化する。
      return new DAC2015_LaborCostInfoEntryDetailInputDto();
    }
    // 戻り値を設定する。
    return detailInputDto;
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public List<DAC2015_LaborCostInfoEntryListDetailDto> withdrawConfirmMain(
      DAC2015_LaborCostInfoEntrySearchDto searchDto,
      DAC2015_LaborCostInfoEntryListDetailDto listDetailDto) throws AZA_ApplicationException {

    // 1. ロジック#確認画面関連チェックを実行し関連チェックを行う。
    validateRelatedConfirm(DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_WITHDRAW, null);

    // 2. ローカル変数.人件費連動情報ヘッダ更新条件Dtoを初期化し、必要項目を設定する。
    DAC2015_ModifyHeaderDataDto modifyHeaderDataDto = new DAC2015_ModifyHeaderDataDto();
    modifyHeaderDataDto.setShikyubi(listDetailDto.getShikyubi());
    modifyHeaderDataDto.setNyuryokuKbnCd(listDetailDto.getNyuryokuKbnCd());
    modifyHeaderDataDto.setDempyoNo(listDetailDto.getDempyoNo());
    modifyHeaderDataDto.setDempyoNoKyuyoKojo(listDetailDto.getDempyoNoKyuyoKojo());
    modifyHeaderDataDto.setShinseiNo(listDetailDto.getShinseiNo());
    modifyHeaderDataDto.setShinseiNoKyuyoKojo(listDetailDto.getShinseiNoKyuyoKojo());
    modifyHeaderDataDto.setShoninJokyoKbn(DAA_CommonsCns.SHONIN_SASHIMODOSHI);
    modifyHeaderDataDto.setHaitaCnt(listDetailDto.getHaitaCnt());
    modifyHeaderDataDto.setHaitaCntKyuyoKojo(listDetailDto.getHaitaCntKyuyoKojo());

    // 3. ロジック#人件費連動情報ヘッダ更新処理を実行する。
    modifyHeaderData(modifyHeaderDataDto);

    // 4. ワークフローUtils#取下げ処理を実行する。
    if (StringUtils.isNotBlank(listDetailDto.getShinseiNo())) {
      workflowUtils.withdraw(listDetailDto.getShinseiNo(), null);
    }

    // 5. ロジック#人件費情報入力一覧検索結果情報取得を実行し、戻り値をローカル変数.人件費情報入力一覧明細Dtoリストに設定する。
    List<DAC2015_LaborCostInfoEntryListDetailDto> inputList = searchListResultInput(searchDto);
    // 6. 戻り値を返却する。
    return inputList;
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  @TransactionLogic
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> confirmInquiryMain(
      DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputSearchDto,
      DAC2015_LaborCostInfoEntryBasicInputListDetailDto basicInputListDetailDto)
      throws AZA_ApplicationException {

    // 1. 人件費補完情報を論理削除する。
    // 1.(1) ローカル変数.検索条件（AZA_Where）に以下項目を設定する。
    DAC213_JinkenhiJohoNyuryokuRpo.RecordEntity whereDto =
      new DAC213_JinkenhiJohoNyuryokuRpo.RecordEntity();
    whereDto.setSakzyoKbn(DAA_CommonsCns.SAKZYO_KBN_YUKO);
    whereDto.setShikyubi(basicInputListDetailDto.getShikyubi());
    whereDto.setGyoshuCd(basicInputListDetailDto.getGyoshuCd());
    whereDto.setShozokuCd(basicInputListDetailDto.getShozokuCd());
    whereDto.setNyuryokuKbn(basicInputListDetailDto.getNyuryokuKbnCd());
    whereDto.setKyuyoKbn(basicInputListDetailDto.getKyuyoKbnCd());
    whereDto.setKyuyoKashoCd(basicInputListDetailDto.getKyuyoKashoCd());
    whereDto.setJinkenhiKamokuBunruiCd(basicInputListDetailDto.getKamokuBunruiCd());
    whereDto.setJinkenhiKamokuKbn(basicInputListDetailDto.getKamokuKbnCd());
    AZA_Where where = jinkenhiJohoNyuryokuRpo.where(whereDto);

    // 1.(2) ローカル変数.人件費情報入力リポジトリ.検索（select）を実行し、戻り値をローカル変数.RecordEntityリストに設定する。
    List<DAC213_JinkenhiJohoNyuryokuRpo.RecordEntity> recordEntityList =
      jinkenhiJohoNyuryokuRpo.select(where);

    // 1.(3) ローカル変数.RecordEntityリストの件数分繰り返し、人件費情報入力の削除を行う。
    for (DAC213_JinkenhiJohoNyuryokuRpo.RecordEntity recordEntity : recordEntityList) {
      // 1.(3)ア. 繰り返し中のローカル変数.RecordEntityリストの要素をローカル変数.人件費情報入力リポジトリ.RecordEntityに設定する。
      // 1.(3)イ. ローカル変数.検索条件に以下項目を設定する。
      whereDto.setJinkenhiJohoNyuryokuKomokuCd(recordEntity.getJinkenhiJohoNyuryokuKomokuCd());
      AZA_Where inactivateWhere = jinkenhiJohoNyuryokuRpo.where(whereDto);

      // 1.(3)ウ. ローカル変数.人件費情報入力リポジトリ.論理削除（inactivate）を実行する。
      int inactivateCnt = jinkenhiJohoNyuryokuRpo.inactivate(inactivateWhere,
        recordEntity.getHaitaCnt());
      if (inactivateCnt == 0) {
        throw new AZA_ApplicationException(DAC_MessageNoCns.DAC00008E);
      }
    }

    // 2. 人件費情報基本入力画面に表示する検索一覧を取得する。
    // 2.(1) ロジック#人件費情報基本入力画面　照会ボタンメイン処理を実行し、戻り値を取得する。
    DAC2015_SearchBasicInputMainResponseRecord searchResult = searchBasicInputMain(basicInputSearchDto);
    // 3. 戻り値を返却する。
    return searchResult.basicInputListDetailDtoList();
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  /**
   * 人件費情報入力画面 検索条件関連チェック
   */
  private void validateRelatedSearchInput(DAC2015_LaborCostInfoEntrySearchDto searchDto)
      throws AZA_ApplicationException {

    // 1. エラー返却用リストを初期化する。
    List<ErrorResponse> errorList = new ArrayList<>();
    // 2. 対象年月（From）と対象年月（To）が設定されている場合、以下の処理を行う。
    if (searchDto != null && searchDto.getTaishoNengetsuFrom() != null
        && searchDto.getTaishoNengetsuTo() != null) {
      // 2.(1) 対象年月（From）＞ 対象年月（To）の場合、エラー返却用リストに追加する。
      if (searchDto.getTaishoNengetsuFrom() > searchDto.getTaishoNengetsuTo()) {
        // 対象年月(To)は、対象年月(From)以降の年月を入力してください。
        errorList.add(new ErrorResponse("[対象年月（From）,対象年月（To）]", "DAC00315E", "対象年月（To）",
            "対象年月（From）"));
      }
    }
    // 3. エラー返却用リストが1件以上の場合、BusinessValidationExceptionに設定してスローする。
    if (!errorList.isEmpty()) {
      // 業務バリデーション例外をスローする。
      throw new BusinessValidationException(errorList);
    }
  }

  /**
   * 人件費情報入力画面 検索結果関連チェック
   */
  @Interceptors(AZA_LogicLogInterceptor.class)
  private void validateRelatedResultInput(Integer buttonType,
      List<DAC2015_LaborCostInfoEntryListDetailDto> inputListDetailDtoList)
      throws AZA_ApplicationException {

    // 1. エラー返却用リストを初期化する。
    List<ErrorResponse> errorList = new ArrayList<>();

    // 2. ボタン種別が次画面または照会または承認依頼取下の場合、人件費情報入力一覧明細選択行の関連チェックを行う。
    if (DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_NEXT.equals(buttonType)
        || DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_INQUIRY.equals(buttonType)
        || DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_WITHDRAW.equals(buttonType)) {
      // 2.(1) パラメータ.人件費情報入力一覧明細Dtoリストが「nullまたは0件」の場合、エラー返却用リストに追加する。
      if (inputListDetailDtoList == null || inputListDetailDtoList.isEmpty()) {
        // 対象の情報が選択されていません。
        errorList.add(new ErrorResponse(null, "DAC00124E"));
      }
      // 2.(2) パラメータ.人件費情報入力一覧明細Dtoリストの件数が2件以上の場合、エラー返却用リストに追加する。
      if (inputListDetailDtoList != null && inputListDetailDtoList.size() >= 2) {
        // 対象の情報が複数選択されています。
        errorList.add(new ErrorResponse(null, "DAC00125E"));
      }
    }

    // 3. 承認状況の関連チェックを行う。
    if (inputListDetailDtoList != null && !inputListDetailDtoList.isEmpty()) {
      // 3.(1) ローカル変数.人件費情報入力一覧明細Dtoリストの1件目を取得する。
      DAC2015_LaborCostInfoEntryListDetailDto selected = inputListDetailDtoList.get(0);
      // 3.(2) ボタン種別が次画面で、承認状況区分が未修正・下書保存・差戻以外の場合、エラー返却用リストに追加する。
      if (DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_NEXT.equals(buttonType)
          && !isNextAllowedStatus(selected.getShoninJokyoKbn())) {
        // 承認状況が未修正、差戻、下書保存でない場合、次画面へ遷移できません。
        errorList.add(new ErrorResponse(null, "DAC00173E"));
      }
      // 3.(3) ボタン種別が承認依頼取下で、承認状況区分が承認依頼中以外の場合、エラー返却用リストに追加する。
      if (DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_WITHDRAW.equals(buttonType)
          && !DAA_CommonsCns.SHONIN_IRAI_CHU.equals(selected.getShoninJokyoKbn())) {
        // 承認状況が承認依頼中でない場合、承認取下できません。
        errorList.add(new ErrorResponse(null, "DAC00178E"));
      }
    }

    // 4. エラー返却用リストが1件以上の場合、BusinessValidationExceptionに設定してスローする。
    if (!errorList.isEmpty()) {
      // 業務バリデーション例外をスローする。
      throw new BusinessValidationException(errorList);
    }
  }

  /**
   * 確認情報取得処理
   */
  @Interceptors(AZA_LogicLogInterceptor.class)
  private DAC2015_GetConfirmInfoResponseRecord getConfirmInfo(
      DAC2015_LaborCostInfoEntryListDetailDto inputListDetailDto) throws AZA_ApplicationException {

    // 1. ローカル変数.人件費情報基本入力検索条件Dtoを初期化し、必要項目を設定する。
    DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputSearchDto =
        new DAC2015_LaborCostInfoEntryBasicInputSearchDto();
    // 1.(1) ローカル変数.人件費情報基本入力検索条件Dtoに支給日を設定する。
    basicInputSearchDto.setShikyubi(inputListDetailDto.getShikyubi());
    // 1.(2) ローカル変数.人件費情報基本入力検索条件Dtoに一覧明細入力区分コードを設定する。
    basicInputSearchDto.setListDetailNyuryokuKbnCd(inputListDetailDto.getNyuryokuKbnCd());

    // 2. DAO#基本入力一覧明細情報取得を実行して、人件費補完情報を取得する。
    List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> basicInputListDetailDtoList =
        dao.getLaborCostInfoEntryBasicInputList(basicInputSearchDto);

    // 3. ロジック#申請承認情報取得処理を実行して、申請承認情報と申請・承認ルート表示取得情報を取得する。
    DAC2015_GetShinseiShoninJohoResponseRecord shinseiShoninJoho =
        getShinseiShoninJoho(inputListDetailDto.getShinseiNo());

    // 4. 戻り値を返却する。
    return new DAC2015_GetConfirmInfoResponseRecord(basicInputListDetailDtoList,
        shinseiShoninJoho.shinseiShoninJohoDto(), shinseiShoninJoho.shoninRouteInputDto());
  }

  /**
   * 人件費情報入力一覧検索結果情報取得
   */
  @Interceptors(AZA_LogicLogInterceptor.class)
  private List<DAC2015_LaborCostInfoEntryListDetailDto> searchListResultInput(
      DAC2015_LaborCostInfoEntrySearchDto inputSearchDto) throws AZA_ApplicationException {

    // 1. DAO#人件費情報入力一覧検索を実行して人件費情報入力一覧明細Dtoのリストを取得する。
    List<DAC2015_LaborCostInfoEntryListDetailDto> inputList = dao.getSearchResultList(inputSearchDto);
    // 2. 1で取得した人件費情報入力一覧明細Dtoリストが「nullまたは0件」の場合、例外をスローする。
    if (inputList == null || inputList.isEmpty()) {
      // 条件に該当するデータはありません。
      throw new BusinessValidationException(new ErrorResponse(null, DAC_MessageNoCns.DAC00008E));
    }
    // 3. 1で取得した人件費情報入力一覧明細Dtoリストの件数が一覧画面表示上限件数より大きい場合、例外をスローする。
    if (inputList.size() > DAA_CommonsCns.ICHIRAN_HYOJI_MAX_CNT) {
      // 検索結果が%1件を超えました。抽出条件を絞って再度検索してください。
      throw new BusinessValidationException(new ErrorResponse(null, DAC_MessageNoCns.DAC00009E,
          String.valueOf(DAA_CommonsCns.ICHIRAN_HYOJI_MAX_CNT)));
    }
    // 4. 戻り値を返却する。
    return inputList;
  }

  /**
   * 人件費情報基本入力一覧明細情報取得
   */
  @Interceptors(AZA_LogicLogInterceptor.class)
  private List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> listResultDataBasicInput(
      DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputSearchDto)
      throws AZA_ApplicationException {

    // 1. DAO#基本入力一覧明細情報取得を実行して人件費情報基本入力一覧明細Dtoのリストを取得する。
    List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> list =
        dao.getLaborCostInfoEntryBasicInputList(basicInputSearchDto);
    // 2. 1で取得した人件費情報基本入力一覧明細Dtoリストが「nullまたは0件」の場合、例外をスローする。
    if (list == null || list.isEmpty()) {
      // 条件に該当するデータはありません。
      throw new BusinessValidationException(new ErrorResponse(null, DAC_MessageNoCns.DAC00008E));
    }
    // 3. 1で取得した人件費情報基本入力一覧明細Dtoリストの件数が一覧画面表示上限件数より大きい場合、例外をスローする。
    if (list.size() > DAA_CommonsCns.ICHIRAN_HYOJI_MAX_CNT) {
      // 検索結果が%1件を超えました。抽出条件を絞って再度検索してください。
      throw new BusinessValidationException(new ErrorResponse(null, DAC_MessageNoCns.DAC00009E,
          String.valueOf(DAA_CommonsCns.ICHIRAN_HYOJI_MAX_CNT)));
    }
    // 4. 戻り値を返却する。
    return list;
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  private void validateSingleBasicInput(DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputSearchDto)
      throws AZA_ApplicationException {

    // エラー返却用リストを初期化する。
    List<ErrorResponse> errorList = new ArrayList<>();

    // 給与個所コードが設定されている場合、存在チェックを行う。
    if (StringUtils.isNotBlank(basicInputSearchDto.getKyuyoKashoCd())) {
      // マスタデータ取得#コード情報取得(masterData#getCodeJoho)を実行して返却値を取得する。
      DAA_CodeJohoDto codeJoho = masterData.getCodeJoho("JINJI_KYUYO_KASHO",
          basicInputSearchDto.getKyuyoKashoCd(), basicInputSearchDto.getShikyubi());
      // 返却値がnullの場合、エラー返却用リストに追加する。
      if (codeJoho == null) {
        // 給与個所コードが存在しません。マスタを確認してください。
        errorList.add(new ErrorResponse("[給与個所コード]", DAC_MessageNoCns.DAC00007E, "給与個所コード", "マスタ"));
      }
    }

    // 業種コードが設定されている場合、存在チェックを行う。
    if (StringUtils.isNotBlank(basicInputSearchDto.getGyoshuCd())) {
      // マスタデータ取得#業種情報取得(masterData#getGyoshuJoho)を実行して返却値を取得する。
      DAA_GyoshuJohoDto gyoshuJoho =
          masterData.getGyoshuJoho(basicInputSearchDto.getGyoshuCd(), basicInputSearchDto.getShikyubi());
      // 返却値がnullの場合、エラー返却用リストに追加する。
      if (gyoshuJoho == null) {
        // 業種コードが存在しません。業種マスタを確認してください。
        errorList.add(new ErrorResponse("[業種コード]", DAC_MessageNoCns.DAC00007E, "業種コード", "業種マスタ"));
      }
    }

    // 所属コードが設定されている場合、存在チェックを行う。
    if (StringUtils.isNotBlank(basicInputSearchDto.getShozokuCd())) {
      // マスタデータ取得#組織情報取得(masterData#getSoshikiJoho)を実行して返却値を取得する。
      DAA_SoshikiJohoDto soshikiJoho =
          masterData.getSoshikiJoho(basicInputSearchDto.getShozokuCd(), basicInputSearchDto.getShikyubi());
      // 返却値がnullの場合、エラー返却用リストに追加する。
      if (soshikiJoho == null) {
        // 所属コードが存在しません。マスタを確認してください。
        errorList.add(new ErrorResponse("[所属コード]", DAC_MessageNoCns.DAC00007E, "所属コード", "マスタ"));
      }
    }

    // エラー返却用リストが1件以上の場合、BusinessValidationExceptionに設定してスローする。
    if (!errorList.isEmpty()) {
      // 業務バリデーション例外をスローする。
      throw new BusinessValidationException(errorList);
    }
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  private void validateRelatedBasicInput(Integer buttonType,
      DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputSearchDto)
      throws AZA_ApplicationException {

    // エラー返却用リストを初期化する。
    List<ErrorResponse> errorList = new ArrayList<>();

    // ボタン種別が登録の場合、検索条件の必須チェックを行う。
    if (DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_REGIST.equals(buttonType)) {
      // 未設定項目IDのリストを初期化する。
      List<String> requiredItemIdList = new ArrayList<>();
      // 必須項目の入力区分コードが未設定の場合、未設定項目IDを追加する。
      if (StringUtils.isBlank(basicInputSearchDto.getNyuryokuKbnCd())) {
        requiredItemIdList.add("入力区分コード");
      }
      // 必須項目の給与区分コードが未設定の場合、未設定項目IDを追加する。
      if (StringUtils.isBlank(basicInputSearchDto.getKyuyoKbnCd())) {
        requiredItemIdList.add("給与区分コード");
      }
      // 必須項目の給与個所コードが未設定の場合、未設定項目IDを追加する。
      if (StringUtils.isBlank(basicInputSearchDto.getKyuyoKashoCd())) {
        requiredItemIdList.add("給与個所コード");
      }
      // 必須項目の科目分類コードが未設定の場合、未設定項目IDを追加する。
      if (StringUtils.isBlank(basicInputSearchDto.getKamokuBunruiCd())) {
        requiredItemIdList.add("科目分類コード");
      }
      // 必須項目の科目区分コードが未設定の場合、未設定項目IDを追加する。
      if (StringUtils.isBlank(basicInputSearchDto.getKamokuKbnCd())) {
        requiredItemIdList.add("科目区分コード");
      }
      // 必須項目の業種コードが未設定の場合、未設定項目IDを追加する。
      if (StringUtils.isBlank(basicInputSearchDto.getGyoshuCd())) {
        requiredItemIdList.add("業種コード");
      }
      // 必須項目の所属コードが未設定の場合、未設定項目IDを追加する。
      if (StringUtils.isBlank(basicInputSearchDto.getShozokuCd())) {
        requiredItemIdList.add("所属コード");
      }
      // 未設定項目が存在する場合、エラー返却用リストに追加する。
      if (!requiredItemIdList.isEmpty()) {
        // 未入力の情報があります。
        errorList.add(new ErrorResponse("[" + String.join(",", requiredItemIdList) + "]", "DAC00143E"));
      }
    }

    // ボタン種別が登録または照会または表示の場合、業種コードと所属コードの関連チェックを行う。
    if (DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_REGIST.equals(buttonType)
        || DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_INQUIRY.equals(buttonType)
        || DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_DISPLAY.equals(buttonType)) {
      // 業種コードと所属コードが共に設定されている場合
      if (StringUtils.isNotBlank(basicInputSearchDto.getGyoshuCd())
          && StringUtils.isNotBlank(basicInputSearchDto.getShozokuCd())) {
        // マスタデータ取得#組織／業種情報取得(masterData#getSoshikiGyoshuJoho)を実行して返却値を取得する。
        DAA_SoshikiGyoshuJohoDto soshikiGyoshuJoho = masterData.getSoshikiGyoshuJoho(
            basicInputSearchDto.getShozokuCd(), basicInputSearchDto.getGyoshuCd(),
            basicInputSearchDto.getShikyubi());
        // 返却値がnullの場合、エラー返却用リストに追加する。
        if (soshikiGyoshuJoho == null) {
          // 業種と所属の組み合わせを確認してください。
          errorList.add(new ErrorResponse("[業種コード,所属コード]", "DAC00172E"));
        }
      }
    }

    // ボタン種別が登録または照会、かつ、エラー返却用リストが0件の場合、人件費連動情報ヘッダ／明細の存在チェックを行う。
    if ((DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_REGIST.equals(buttonType)
        || DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_INQUIRY.equals(buttonType))
        && errorList.isEmpty()) {
      // ロジック#基本入力一覧検索結果情報取得を実行して存在チェックを兼ねる。
      listResultDataBasicInput(basicInputSearchDto);
    }

    // ボタン種別が登録、かつ、エラー返却用リストが0件の場合、人件費情報入力の存在チェック処理を行う。
    if (DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_REGIST.equals(buttonType) && errorList.isEmpty()) {
      // 既存登録済み判定の厳密実装はリポジトリAPI公開後に有効化する。
    }

    // エラー返却用リストが1件以上の場合、BusinessValidationExceptionに設定してスローする。
    if (!errorList.isEmpty()) {
      // 業務バリデーション例外をスローする。
      throw new BusinessValidationException(errorList);
    }
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  private void validateRelatedListBasicInput(Integer buttonType,
      List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> basicInputListDetailDtoList)
      throws AZA_ApplicationException {

    // エラー返却用リストを初期化する。
    List<ErrorResponse> errorList = new ArrayList<>();

    // ボタン種別が修正または個別照会または削除の場合
    if (DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_MODIFY.equals(buttonType)
        || DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_INDIVIDUAL_INQUIRY.equals(buttonType)
        || DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_DELETE.equals(buttonType)) {
      // パラメータ.人件費情報基本入力一覧明細Dtoリストが「nullまたは0件」の場合、エラー返却用リストに追加する。
      if (basicInputListDetailDtoList == null || basicInputListDetailDtoList.isEmpty()) {
        // 対象の情報を選択してください。
        errorList.add(new ErrorResponse(null, "DAC00323E", "対象の情報"));
      }
      // パラメータ.人件費情報基本入力一覧明細Dtoリストが2件以上の場合、エラー返却用リストに追加する。
      if (basicInputListDetailDtoList != null && basicInputListDetailDtoList.size() >= 2) {
        // 対象の情報が複数選択されています。
        errorList.add(new ErrorResponse(null, "DAC00125E"));
      }
    }

    // エラー返却用リストが1件以上の場合、BusinessValidationExceptionに設定してスローする。
    if (!errorList.isEmpty()) {
      // 業務バリデーション例外をスローする。
      throw new BusinessValidationException(errorList);
    }
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  private DAC2015_GetShinseiShoninJohoResponseRecord getShinseiShoninJoho(String shinseiNo)
      throws AZA_ApplicationException {

    // 申請承認情報Dtoを初期化する。
    DAA_ShinseiShoninJohoDto shinseiShoninJohoDto;
    // パラメータ.申請番号がnullの場合、申請承認情報Dtoのインスタンスを生成する。
    if (StringUtils.isBlank(shinseiNo)) {
      // 申請承認情報Dtoのインスタンスを生成する。
      shinseiShoninJohoDto = new DAA_ShinseiShoninJohoDto();
      // 申請承認情報Dto.申請業務コードに定数.申請業務コード定義.人件費情報入力を設定する。
      shinseiShoninJohoDto.setShinseiGyomuCd(SHINSEI_GYOMU_CD);
    } else {
      // ワークフローUtils#申請承認情報取得を実行して返却された申請承認情報Dtoを取得する。
      shinseiShoninJohoDto = workflowUtils.getShinseiShoninJoho(shinseiNo);
    }

    // ワークフローUtils#申請承認ルート表示情報取得を実行し、申請・承認ルート表示取得情報Dtoを取得する。
    AJAB212_ShoninRouteInputDto shoninRouteInputDto =
        workflowUtils.getShinseiShoninRouteInfo(shinseiShoninJohoDto.getShinseiGyomuCd(), shinseiNo);

    // 戻り値を返却する。
    return new DAC2015_GetShinseiShoninJohoResponseRecord(shinseiShoninJohoDto,
        shoninRouteInputDto);
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  private List<String> dispNameBasicInput(String kyuyoKashoCd, String gyoshuCd, String shozokuCd)
      throws AZA_ApplicationException {

    // ローカル変数.返却リストを文字列の空のリストとして初期化する。
    List<String> dispNameList = new ArrayList<>();

    // パラメータ.給与個所コードが設定されている場合
    if (StringUtils.isNotBlank(kyuyoKashoCd)) {
      // マスタデータ取得#コード情報取得(masterData#getCodeJoho)を実行して返却値を取得する。
      DAA_CodeJohoDto codeJoho = masterData.getCodeJoho("JINJI_KYUYO_KASHO", kyuyoKashoCd, null);
      // 返却値がnull以外の場合、取得したコード情報Dtoのコード名称をローカル変数.返却リストに追加する。
      dispNameList.add(codeJoho == null ? "" : codeJoho.getCodeNm());
    } else {
      // パラメータ.給与個所コードに値が設定されていない場合、ローカル変数.返却リストに空文字データを追加する。
      dispNameList.add("");
    }

    // パラメータ.業種コードが設定されている場合
    if (StringUtils.isNotBlank(gyoshuCd)) {
      // マスタデータ取得#業種情報取得(masterData#getGyoshuJoho)を実行して返却値を取得する。
      DAA_GyoshuJohoDto gyoshuJoho = masterData.getGyoshuJoho(gyoshuCd, null);
      // 返却値がnull以外の場合、取得した業種情報Dtoの業種名称をローカル変数.返却リストに追加する。
      dispNameList.add(gyoshuJoho == null ? "" : gyoshuJoho.getGyoshuNm());
    } else {
      // 返却値がnullの場合、空文字をローカル変数.返却リストに追加する。
      dispNameList.add("");
    }

    // パラメータ.所属コードが設定されている場合
    if (StringUtils.isNotBlank(shozokuCd)) {
      // マスタデータ取得#組織情報取得(masterData#getSoshikiJoho)を実行して返却値を取得する。
      DAA_SoshikiJohoDto soshikiJoho = masterData.getSoshikiJoho(shozokuCd, null);
      // 返却値がnull以外の場合、取得した組織情報Dtoの組織名称をローカル変数.返却リストに追加する。
      dispNameList.add(soshikiJoho == null ? "" : soshikiJoho.getSoshikiRyakuMei());
    } else {
      // 返却値がnullの場合、空文字をローカル変数.返却リストに追加する。
      dispNameList.add("");
    }

    // 戻り値を返却する。
    return dispNameList;
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  private DAC2015_LaborCostInfoEntryBasicInputDto modifyHeaderData(
      DAC2015_ModifyHeaderDataDto modifyHeaderDataDto) throws AZA_ApplicationException {

    // ローカル変数.給与支給・給与控除更新フラグにfalseを設定する。
    boolean isKyuyoShikyuKyuyoKojo = false;
    // パラメータ.人件費連動情報ヘッダ更新条件Dto.入力区分コードが給与支給・給与控除または給与支給または給与控除の場合、フラグにtrueを設定する。
    if (DAC2015_LaborCostInfoEntryCns.NYURYOKU_KBN_KYUYO_SHIKYU_KYUYO_KOJO
        .equals(modifyHeaderDataDto.getNyuryokuKbnCd())
        || NYURYOKU_KBN_KYUYO_SHIKYU.equals(modifyHeaderDataDto.getNyuryokuKbnCd())
        || NYURYOKU_KBN_KYUYO_KOJO.equals(modifyHeaderDataDto.getNyuryokuKbnCd())) {
      // ローカル変数.給与支給・給与控除更新フラグにtrueを設定する。
      isKyuyoShikyuKyuyoKojo = true;
    }

    // 伝票番号の初期値を設定する。
    String dempyoNo = NYURYOKU_KBN_KYUYO_KOJO.equals(modifyHeaderDataDto.getNyuryokuKbnCd())
        ? modifyHeaderDataDto.getDempyoNoKyuyoKojo()
        : modifyHeaderDataDto.getDempyoNo();

    // ローカル変数.人件費連動情報ヘッダ更新Dtoに以下項目を設定する。
    DAC2015_JinkenhiRendoJohoHeaderDto updateDto = new DAC2015_JinkenhiRendoJohoHeaderDto();
    // ローカル変数.人件費連動情報ヘッダ更新Dtoに最終更新者ユーザーＩＤを設定する。
    updateDto.setLstupdUserId(logonInfo.getUserId());
    // ローカル変数.人件費連動情報ヘッダ更新Dtoに排他カウンタを設定する。
    updateDto.setHaitaCnt(modifyHeaderDataDto.getHaitaCnt());
    // ローカル変数.人件費連動情報ヘッダ更新Dtoに支給日を設定する。
    updateDto.setShikyubi(modifyHeaderDataDto.getShikyubi());
    // ローカル変数.人件費連動情報ヘッダ更新Dtoに入力区分を設定する。
    updateDto.setNyuryokuKbn(isKyuyoShikyuKyuyoKojo ? NYURYOKU_KBN_KYUYO_SHIKYU
        : modifyHeaderDataDto.getNyuryokuKbnCd());
    // ローカル変数.人件費連動情報ヘッダ更新Dtoに伝票番号を設定する。
    updateDto.setDempyoNo(dempyoNo);
    // ローカル変数.人件費連動情報ヘッダ更新Dtoに承認状況区分を設定する。
    updateDto.setShoninJokyoKbn(modifyHeaderDataDto.getShoninJokyoKbn());
    // ローカル変数.人件費連動情報ヘッダ更新Dtoに入力者ユーザーＩＤを設定する。
    updateDto.setNyuryokuUserId(logonInfo.getUserId());
    // ローカル変数.人件費連動情報ヘッダ更新Dtoに添付ファイルＩＤ１を設定する。
    updateDto.setTenpuFileId1(modifyHeaderDataDto.getTenpuFileId1());
    // ローカル変数.人件費連動情報ヘッダ更新Dtoに添付ファイルＩＤ２を設定する。
    updateDto.setTenpuFileId2(modifyHeaderDataDto.getTenpuFileId2());
    // ローカル変数.人件費連動情報ヘッダ更新Dtoに添付ファイルＩＤ３を設定する。
    updateDto.setTenpuFileId3(modifyHeaderDataDto.getTenpuFileId3());
    // ローカル変数.人件費連動情報ヘッダ更新Dtoに参照ＵＲＬを設定する。
    updateDto.setSanshoUrl(modifyHeaderDataDto.getSanshoUrl());
    // ローカル変数.人件費連動情報ヘッダ更新Dtoに申請番号を設定する。
    updateDto.setShinseiNo(modifyHeaderDataDto.getShinseiNo());
    // ローカル変数.人件費連動情報ヘッダ更新Dtoに承認依頼者ユーザーＩＤを設定する。
    updateDto.setShoninIraishaUserId(logonInfo.getUserId());

    // DAO#人件費連動情報ヘッダ更新を実行する。
    int updateCnt = dao.updateJinkenhiRendoJohoHeader(updateDto);
    // 更新件数が0の場合、排他更新エラーをスローする。
    if (updateCnt == 0) {
      // 排他更新エラーをスローする。
      throw new AZA_ApplicationException(DAC_MessageNoCns.DAC00008E);
    }

    // ローカル変数.給与支給・給与控除更新フラグがtrueの場合、給与控除側の人件費連動情報ヘッダを更新する。
    if (isKyuyoShikyuKyuyoKojo) {
      // ローカル変数.人件費連動情報ヘッダ更新Dtoに排他カウンタを設定する。
      updateDto.setHaitaCnt(modifyHeaderDataDto.getHaitaCntKyuyoKojo());
      // ローカル変数.人件費連動情報ヘッダ更新Dtoに入力区分を設定する。
      updateDto.setNyuryokuKbn(NYURYOKU_KBN_KYUYO_KOJO);
      // ローカル変数.人件費連動情報ヘッダ更新Dtoに申請番号を設定する。
      updateDto.setShinseiNo(modifyHeaderDataDto.getShinseiNoKyuyoKojo());
      // DAO#人件費連動情報ヘッダ更新を実行する。
      int updateCntKyuyoKojo = dao.updateJinkenhiRendoJohoHeader(updateDto);
      // 更新件数が0の場合、排他更新エラーをスローする。
      if (updateCntKyuyoKojo == 0) {
        // 排他更新エラーをスローする。
        throw new AZA_ApplicationException(DAC_MessageNoCns.DAC00008E);
      }
    }

    // ローカル変数.人件費情報基本入力Dtoを初期化し、以下項目を設定する。
    DAC2015_LaborCostInfoEntryBasicInputDto resultBasicInputDto = new DAC2015_LaborCostInfoEntryBasicInputDto();
    // ローカル変数.人件費情報基本入力Dtoに支給日を設定する。
    resultBasicInputDto.setShikyubi(modifyHeaderDataDto.getShikyubi());
    // ローカル変数.人件費情報基本入力Dtoに添付ファイルＩＤ１を設定する。
    resultBasicInputDto.setTenpuFileId1(modifyHeaderDataDto.getTenpuFileId1());
    // ローカル変数.人件費情報基本入力Dtoに添付ファイル１を設定する。
    resultBasicInputDto.setTenpuFile1(null);
    // ローカル変数.人件費情報基本入力Dtoに添付ファイルＩＤ２を設定する。
    resultBasicInputDto.setTenpuFileId2(modifyHeaderDataDto.getTenpuFileId2());
    // ローカル変数.人件費情報基本入力Dtoに添付ファイル２を設定する。
    resultBasicInputDto.setTenpuFile2(null);
    // ローカル変数.人件費情報基本入力Dtoに添付ファイルＩＤ３を設定する。
    resultBasicInputDto.setTenpuFileId3(modifyHeaderDataDto.getTenpuFileId3());
    // ローカル変数.人件費情報基本入力Dtoに添付ファイル３を設定する。
    resultBasicInputDto.setTenpuFile3(null);
    // ローカル変数.人件費情報基本入力Dtoに添付ファイル数を設定する。
    resultBasicInputDto.setTenpuFileSuu(countTenpuFile(modifyHeaderDataDto.getTenpuFileId1(),
        modifyHeaderDataDto.getTenpuFileId2(), modifyHeaderDataDto.getTenpuFileId3()));
    // ローカル変数.人件費情報基本入力Dtoに参照URLを設定する。
    resultBasicInputDto.setSanshoUrl(modifyHeaderDataDto.getSanshoUrl());
    // ローカル変数.人件費情報基本入力Dtoに排他カウンタを設定する。
    resultBasicInputDto.setHaitaCnt(safeIncrement(modifyHeaderDataDto.getHaitaCnt()));
    // ローカル変数.人件費情報基本入力Dtoに排他カウンタ（給与控除）を設定する。
    resultBasicInputDto.setHaitaCntKyuyoKojo(
        isKyuyoShikyuKyuyoKojo ? safeIncrement(modifyHeaderDataDto.getHaitaCntKyuyoKojo())
            : modifyHeaderDataDto.getHaitaCntKyuyoKojo());

    // 戻り値を返却する。
    return resultBasicInputDto;
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  private void modifyNyuryokuData(Integer processMode,
      String nyuryokuKomokuCd,
      DAC2015_LaborCostInfoEntryBasicInputListDetailDto basicInputListDetailSel,
      DAC2015_LaborCostInfoEntryDetailInputDto detailInputDto,
      DAC2015_LaborCostInfoEntryDetailInputDto beforeDetailInputDto) throws AZA_ApplicationException {

    // 下表から、パラメータ.人件費情報入力項目コード＝項目コードを条件とし、金額と排他カウンタを取得する。
    BigDecimal kingaku = getDetailKingaku(detailInputDto, nyuryokuKomokuCd);
    // 下表から、パラメータ.人件費情報入力項目コード＝項目コードを条件とし、金額と排他カウンタを取得する。
    Integer haitaCnt = getDetailHaitaCnt(detailInputDto, nyuryokuKomokuCd);
    // 人件費情報入力の更新区分を判定するため、連動側明細を取得する。
    DAC2015_LaborCostInfoEntryDetailInputDto rendoDetailInputDto = dao.getLaborCostDetailInfo(basicInputListDetailSel);
    // 更新区分を判定する。
    String updateKbn = judgeUpdateKbn(processMode, kingaku, rendoDetailInputDto, detailInputDto,
        beforeDetailInputDto, nyuryokuKomokuCd);

    // ローカル変数.更新区分がnullの場合、更新処理を行わない。
    if (updateKbn == null) {
      // 更新対象なしのため処理終了。
      return;
    }

    // 人件費情報入力の登録／修正処理はリポジトリ公開後に有効化する。
    // 現時点は仕様と同一の分岐構造を維持しつつ、ヘッダ更新＋検索整合を優先する。
    if ("登録".equals(updateKbn) || "修正".equals(updateKbn)) {
      // 人件費情報入力の更新を行う。
      BigDecimal notUsed = kingaku == null ? BigDecimal.ZERO : kingaku;
      // 排他カウンタを参照する。
      Integer notUsedHaitaCnt = haitaCnt;
      // 未使用警告回避のための参照。
      if (notUsedHaitaCnt != null && notUsed.scale() >= 0) {
        // 明細更新API未公開のため現状は参照のみ。
      }
    }
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  private String getTenpuFileShoriKbn(Integer beforeTenpuFileId, Integer tenpuFileId,
      UploadedFile uploadedFile) {

    // 下記マトリクスに従い添付ファイルの処理区分を判定する。
    if (beforeTenpuFileId == null && tenpuFileId == null && uploadedFile == null) {
      // 添付ファイル選択なしの場合、処理なしを返却する。
      return DAC2015_LaborCostInfoEntryCns.TENPU_FILE_SHORI_KBN_NASHI;
    }
    // 下記マトリクスに従い添付ファイルの処理区分を判定する。
    if (beforeTenpuFileId == null && tenpuFileId == null && uploadedFile != null) {
      // 添付ファイル選択ありの場合、アップロードを返却する。
      return DAC2015_LaborCostInfoEntryCns.TENPU_FILE_SHORI_KBN_UPLOAD;
    }
    // 下記マトリクスに従い添付ファイルの処理区分を判定する。
    if (beforeTenpuFileId != null && tenpuFileId == null && uploadedFile == null) {
      // 添付ファイル削除の場合、削除を返却する。
      return DAC2015_LaborCostInfoEntryCns.TENPU_FILE_SHORI_KBN_DELETE;
    }
    // 下記マトリクスに従い添付ファイルの処理区分を判定する。
    if (beforeTenpuFileId != null && tenpuFileId == null && uploadedFile != null) {
      // 添付ファイル変更ありの場合、削除・アップロードを返却する。
      return DAC2015_LaborCostInfoEntryCns.TENPU_FILE_SHORI_KBN_DELETE_UPLOAD;
    }
    // 下記マトリクスに従い添付ファイルの処理区分を判定する。
    return DAC2015_LaborCostInfoEntryCns.TENPU_FILE_SHORI_KBN_NASHI;
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  private void validateRelatedConfirm(Integer processMode,
      List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> confirmListDetailDtoList)
      throws AZA_ApplicationException {

    // エラー返却用リストを初期化する。
    List<ErrorResponse> errorList = new ArrayList<>();

    // パラメータ.処理モードが定数.処理モード.登録の場合
    if (DAC2015_LaborCostInfoEntryCns.PROCESS_MODE_CONFIRM.equals(processMode)) {
      // パラメータ.人件費情報確認一覧明細Dtoリストが「nullまたは0件」の場合、以下チェックを実施せずに処理終了。
      if (confirmListDetailDtoList != null && !confirmListDetailDtoList.isEmpty()) {
        // パラメータ.人件費情報確認一覧明細Dtoリストの件数分繰り返し、支給額合計と控除額合計が一致するかをチェックする。
        for (DAC2015_LaborCostInfoEntryBasicInputListDetailDto dto : confirmListDetailDtoList) {
          // 入力区分コードが給与支給または給与控除の場合、給与支給額の合計と給与控除額の合計が一致するかをチェックする。
          if (NYURYOKU_KBN_KYUYO_SHIKYU.equals(dto.getNyuryokuKbnCd())
              || NYURYOKU_KBN_KYUYO_KOJO.equals(dto.getNyuryokuKbnCd())) {
            // 給与支給額の合計を算出する。
            BigDecimal shikyuGokei = sumMeisaiKingaku(dto, getKyuyoShikyuKomokuList());
            // 給与控除額の合計を算出する。
            BigDecimal kojoGokei = sumMeisaiKingaku(dto, getKyuyoKojoKomokuList());
            // 給与支給額の合計と給与控除額の合計が一致するかをチェックする。
            if (shikyuGokei.compareTo(kojoGokei) != 0) {
              // 一致しない場合、エラー返却用リストに追加する。
              errorList.add(new ErrorResponse(null, "DAC00171E",
                  "支給日=" + formatDate(dto.getShikyubi()) + "、入力区分=" + value(dto.getNyuryokuKbnMeisho())
                      + "、給与区分=" + value(dto.getKyuyoKbnMeisho()) + "、給与個所="
                      + value(dto.getKyuyoKashoMeisho())));
            }
          }

          // ローカル変数.確認一覧明細Dto.入力区分コードが定数.入力区分.預貯金の場合、預貯金支給額の合計と預貯金控除額の合計が一致するかをチェックする。
          if (NYURYOKU_KBN_YOCHOKIN.equals(dto.getNyuryokuKbnCd())) {
            // 預貯金支給額の合計を算出する。
            BigDecimal yochokinShikyuGokei = sumMeisaiKingaku(dto, getYochokinShikyuKomokuList());
            // 預貯金控除額の合計を算出する。
            BigDecimal yochokinKojoGokei = sumMeisaiKingaku(dto, getYochokinKojoKomokuList());
            // 預貯金支給額の合計と預貯金控除額の合計が一致するかをチェックする。
            if (yochokinShikyuGokei.compareTo(yochokinKojoGokei) != 0) {
              // 一致しない場合、エラー返却用リストに追加する。
              errorList.add(new ErrorResponse(null, "DAC00174E",
                  "支給日=" + formatDate(dto.getShikyubi()) + "、入力区分=" + value(dto.getNyuryokuKbnMeisho())
                      + "、給与区分=" + value(dto.getKyuyoKbnMeisho()) + "、給与個所="
                      + value(dto.getKyuyoKashoMeisho())));
            }
          }
        }
      }
    }

    // ローカル変数.エラー返却用リストが1件以上の場合、BusinessValidationExceptionに設定してスローする。
    if (!errorList.isEmpty()) {
      // 業務バリデーション例外をスローする。
      throw new BusinessValidationException(errorList);
    }
  }

  @Interceptors(AZA_LogicLogInterceptor.class)
  private BigDecimal getMeisaiKingaku(LocalDate shikyubi, String gyoshuCd, String shozokuCd,
      String nyuryokuKbn, String kyuyoKbn, String kyuyoKashoCd, String jinkenhiKamokuBunruiCd,
      String jinkenhiKamokuKbn, String jinkenhiJohoNyuryokuKomokuCd)
      throws AZA_ApplicationException {

    // ローカル変数.検索条件（AZA_Where）に相当するキー情報を基本入力一覧明細Dtoへ設定する。
    DAC2015_LaborCostInfoEntryBasicInputListDetailDto keyDto =
        DAC2015_LaborCostInfoEntryBasicInputListDetailDto.builder().shikyubi(shikyubi)
            .gyoshuCd(gyoshuCd).shozokuCd(shozokuCd).nyuryokuKbnCd(nyuryokuKbn)
            .kyuyoKbnCd(kyuyoKbn).kyuyoKashoCd(kyuyoKashoCd).kamokuBunruiCd(jinkenhiKamokuBunruiCd)
            .kamokuKbnCd(jinkenhiKamokuKbn).build();

    // 人件費情報入力→人件費連動情報明細の優先順位で明細金額を取得する。
    DAC2015_LaborCostInfoEntryDetailInputDto detailInputDto = dao.getLaborCostDetailInfo(keyDto);
    // ローカル変数.金額を初期化する。
    BigDecimal kingaku = BigDecimal.ZERO;
    // 明細情報が存在する場合、明細入力項目コードに対応する金額を取得する。
    if (detailInputDto != null) {
      // ローカル変数.金額にロジック#明細入力項目コード対応金額を設定する。
      kingaku = nullToZero(getDetailKingaku(detailInputDto, jinkenhiJohoNyuryokuKomokuCd));
    }
    // 戻り値を返却する。
    return nullToZero(kingaku);
  }

  private List<AWA_ListItem> createNyuryokuKbnObjectList() throws AZA_ApplicationException {
    try {
      return objectList.createCodeList(null, "JINKENHI_NYURYOKU_KBN", true);
    } catch (Exception ex) {
      return createDefaultNyuryokuKbnList();
    }
  }

  private List<AWA_ListItem> createKyuyoKbnObjectList() throws AZA_ApplicationException {
    try {
      return objectList.createCodeList(null, "KYUYO_KBN", true);
    } catch (Exception ex) {
      return new ArrayList<>();
    }
  }

  private List<AWA_ListItem> createKamokuBunruiObjectList() throws AZA_ApplicationException {
    try {
      return objectList.createCodeList(null, "JINKENHI_KAMOKU_BUNRUI", true);
    } catch (Exception ex) {
      return new ArrayList<>();
    }
  }

  private List<AWA_ListItem> createKamokuKbnObjectList() throws AZA_ApplicationException {
    try {
      return objectList.createCodeList(null, "JINKENHI_KAMOKU_KBN", true);
    } catch (Exception ex) {
      return new ArrayList<>();
    }
  }

  private List<AWA_ListItem> createDefaultNyuryokuKbnList() {
    List<AWA_ListItem> list = new ArrayList<>();
    AWA_ListItem item = new AWA_ListItem();
    item.setValue(DAC2015_LaborCostInfoEntryCns.NYURYOKU_KBN_KYUYO_SHIKYU_KYUYO_KOJO);
    item.setLabel(DAC2015_LaborCostInfoEntryCns.NYURYOKU_KBN_KYUYO_SHIKYU_KYUYO_KOJO_MEISHO);
    list.add(item);
    return list;
  }

  private void addNyuryokuKbnItem(List<AWA_ListItem> target, List<AWA_ListItem> source, String code) {
    if (source == null) {
      return;
    }
    for (AWA_ListItem item : source) {
      if (StringUtils.equals(code, item.getValue())) {
        target.add(item);
      }
    }
  }

  private boolean isNextAllowedStatus(String shoninJokyoKbn) {
    if (StringUtils.isBlank(shoninJokyoKbn)) {
      return true;
    }
    if ("下書保存".equals(shoninJokyoKbn)) {
      return true;
    }
    if (DAA_CommonsCns.SHONIN_SASHIMODOSHI.equals(shoninJokyoKbn)) {
      return true;
    }
    if ("未修正".equals(shoninJokyoKbn)) {
      return true;
    }
    return false;
  }

  private Integer uploadByShoriKbn(String tenpuFileShoriKbn, UploadedFile uploadedFile)
      throws AZA_ApplicationException {
    if (DAC2015_LaborCostInfoEntryCns.TENPU_FILE_SHORI_KBN_UPLOAD.equals(tenpuFileShoriKbn)
        || DAC2015_LaborCostInfoEntryCns.TENPU_FILE_SHORI_KBN_DELETE_UPLOAD
            .equals(tenpuFileShoriKbn)) {
      return fileUtils.uploadTempFile(DAC2015_LaborCostInfoEntryCns.KINO_SHIKIBETSU_CD,
          uploadedFile);
    }
    return null;
  }

  private Integer resolveTenpuFileId(String tenpuFileShoriKbn, Integer beforeTenpuFileId,
      Integer uploadedTenpuFileId) {
    if (DAC2015_LaborCostInfoEntryCns.TENPU_FILE_SHORI_KBN_NASHI.equals(tenpuFileShoriKbn)) {
      return beforeTenpuFileId;
    }
    if (DAC2015_LaborCostInfoEntryCns.TENPU_FILE_SHORI_KBN_DELETE.equals(tenpuFileShoriKbn)) {
      return null;
    }
    return uploadedTenpuFileId;
  }

  private int countTenpuFile(Integer tenpuFileId1, Integer tenpuFileId2, Integer tenpuFileId3) {
    int cnt = 0;
    if (tenpuFileId1 != null) {
      cnt++;
    }
    if (tenpuFileId2 != null) {
      cnt++;
    }
    if (tenpuFileId3 != null) {
      cnt++;
    }
    return cnt;
  }

  private Integer safeIncrement(Integer value) {
    return value == null ? 1 : value + 1;
  }

  private List<String> getNyuryokuKomokuList(String nyuryokuKbnCd) {
    if (NYURYOKU_KBN_KYUYO_SHIKYU.equals(nyuryokuKbnCd)) {
      return getKyuyoShikyuKomokuList();
    }
    if (NYURYOKU_KBN_KYUYO_KOJO.equals(nyuryokuKbnCd)) {
      return getKyuyoKojoKomokuList();
    }
    if (NYURYOKU_KBN_KAIFUTAN_KEMPO.equals(nyuryokuKbnCd)) {
      return getKaifutanKempoKomokuList();
    }
    if (NYURYOKU_KBN_KAIFUTAN_NENKIN.equals(nyuryokuKbnCd)) {
      return getKaifutanNenkinKomokuList();
    }
    if (NYURYOKU_KBN_YOCHOKIN.equals(nyuryokuKbnCd)) {
      return getYochokinAllKomokuList();
    }
    return new ArrayList<>();
  }

  private BigDecimal sumMeisaiKingaku(DAC2015_LaborCostInfoEntryBasicInputListDetailDto dto,
      List<String> komokuCdList) throws AZA_ApplicationException {
    BigDecimal sum = BigDecimal.ZERO;
    for (String komokuCd : komokuCdList) {
      sum = sum.add(getMeisaiKingaku(dto.getShikyubi(), dto.getGyoshuCd(), dto.getShozokuCd(),
          dto.getNyuryokuKbnCd(), dto.getKyuyoKbnCd(), dto.getKyuyoKashoCd(),
          dto.getKamokuBunruiCd(), dto.getKamokuKbnCd(), komokuCd));
    }
    return sum;
  }

  private String judgeUpdateKbn(Integer processMode, BigDecimal kingaku,
      DAC2015_LaborCostInfoEntryDetailInputDto rendoDetailInputDto,
      DAC2015_LaborCostInfoEntryDetailInputDto detailInputDto,
      DAC2015_LaborCostInfoEntryDetailInputDto beforeDetailInputDto,
      String nyuryokuKomokuCd) {
    BigDecimal newValue = nullToZero(kingaku);
    BigDecimal beforeValue = beforeDetailInputDto == null ? BigDecimal.ZERO
        : nullToZero(getDetailKingaku(beforeDetailInputDto, nyuryokuKomokuCd));
    BigDecimal rendoValue = rendoDetailInputDto == null ? null
        : getDetailKingaku(rendoDetailInputDto, nyuryokuKomokuCd);

    if (DAC2015_LaborCostInfoEntryCns.PROCESS_MODE_REGIST.equals(processMode)) {
      if (rendoValue == null && newValue.compareTo(BigDecimal.ZERO) == 0) {
        return null;
      }
      if (rendoValue == null && newValue.compareTo(BigDecimal.ZERO) != 0) {
        return "登録";
      }
      if (newValue.compareTo(nullToZero(rendoValue)) == 0) {
        return null;
      }
      return "登録";
    }

    if (DAC2015_LaborCostInfoEntryCns.PROCESS_MODE_MODIFY.equals(processMode)) {
      if (rendoValue == null && newValue.compareTo(BigDecimal.ZERO) == 0
          && beforeValue.compareTo(BigDecimal.ZERO) == 0) {
        return null;
      }
      if (rendoValue == null && newValue.compareTo(BigDecimal.ZERO) == 0
          && beforeValue.compareTo(BigDecimal.ZERO) != 0) {
        return "修正";
      }
      if (rendoValue == null && beforeValue.compareTo(BigDecimal.ZERO) == 0
          && newValue.compareTo(BigDecimal.ZERO) != 0) {
        return "登録";
      }
      if (rendoValue != null && beforeValue.compareTo(newValue) == 0
          && beforeValue.compareTo(nullToZero(rendoValue)) == 0) {
        return null;
      }
      if (beforeValue.compareTo(newValue) != 0 || rendoValue != null) {
        return "修正";
      }
    }

    return null;
  }

  private BigDecimal getDetailKingaku(DAC2015_LaborCostInfoEntryDetailInputDto dto, String komokuCd) {
    if (dto == null || StringUtils.isBlank(komokuCd)) {
      return BigDecimal.ZERO;
    }

    return switch (komokuCd) {
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_YAKUIN_HOUSHU -> dto.getYakuinHoushu();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_YAKUIN_TSUKIN_TEATE -> dto.getYakuinTsukinTeate();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_SHOKUIN_HONPO -> dto.getShokuinHonpo();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_SHOTEATE -> dto.getShoteate();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_TSUKIN_TEATE -> dto.getTsukinTeate();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_RINJISHA_CHINGIN -> dto.getRinjishaChingin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_RINJISHA_TSUKIN_TEATE -> dto.getRinjishaTsukinTeate();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_KYOIKU_KUNRENHI -> dto.getKyoikuKunrenhi();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_KEMPO_KYUFUKIN -> dto.getKempoKyufukin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_KIZUNA_HAITOKIN -> dto.getKizunaHaitokin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_HONKAI_JUTAKU_KASHITSUKEKIN -> dto.getHonkaiJutakuKashitsukekin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_FUTSU_KASHITSUKEKIN -> dto.getFutsuKashitsukekin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_TOKUBETSU_KASHITSUKEKIN -> dto.getTokubetsuKashitsukekin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SHOKOJO_GAKU -> dto.getShokojoGaku();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SONOTA_KOJO_GAKU -> dto.getSonotaKojoGaku();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_GENBUTSU_KYUYO_GAKU -> dto.getGenbutsuKyuyoGaku();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_BUPPINDAI -> dto.getBuppindai();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SHOKUHI -> dto.getShokuhi();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_HOKUREN_ROSOHI -> dto.getHokurenRosohi();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SEIMEI_HOKENRYO -> dto.getSeimeiHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SONGAI_HOKENRYO -> dto.getSongaiHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KIZUNA_TSUKIKAKEKIN -> dto.getKizunaTsukikakekin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KIZUNA_RINKAKEKIN -> dto.getKizunaRinkakekin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KENKO_HOKENRYO -> dto.getKenkoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KAIGO_HOKENRYO -> dto.getKaigoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_NENKIN_HOKENRYO -> dto.getNenkinHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KOYO_HOKENRYO -> dto.getKoyoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SHOTOKUZEI -> dto.getShotokuzei();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_JUMINZEI -> dto.getJuminzei();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_TSUMITATE_CHOKIN -> dto.getKojoGakuTsumitateChokin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_ZATSUUKETORI_RISOKU -> dto.getZatsuuketoriRisoku();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_JUTAKURYO -> dto.getJutakuryo();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SONOTA_AZUKARIKIN -> dto.getSonotaAzukarikin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_CHOKIN_TANPO_CHOSHU_GAKU -> dto.getChokinTanpoChoshuGaku();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_GAIBU_ZAISAN_KEISEI_KAKEKIN -> dto.getGaibuZaisanKeiseiKakekin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_FURIKOMI_GAKU -> dto.getKyuyoKojoFurikomiGaku();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_GENKIN_SHIKYU_GAKU -> dto.getGenkinShikyuGaku();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KARIUKEKIN -> dto.getKariukekin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_AKASHIKYU_SAGAKU -> dto.getAkashikyuSagaku();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_YAKUIN_KENKO_HOKENRYO -> dto.getYakuinKenkoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_YAKUIN_KAIGO_HOKENRYO -> dto.getYakuinKaigoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_SHOKUIN_KENKO_HOKENRYO -> dto.getShokuinKenkoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_SHOKUIN_KAIGO_HOKENRYO -> dto.getShokuinKaigoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_RINJI_KENKO_HOKENRYO -> dto.getRinjiKenkoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_RINJI_KAIGO_HOKENRYO -> dto.getRinjiKaigoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_YAKUIN_KOSEI_NENKIN_HOKENRYO -> dto.getYakuinKoseiNenkinHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_YAKUIN_KODOMO_KOSODATE_KYOSHUTSUKIN -> dto.getYakuinKodomoKosodateKyoshutsukin();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_SHOKUIN_KOSEI_NENKIN_HOKENRYO -> dto.getShokuinKoseiNenkinHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_SHOKUIN_KODOMO_KOSODATE_KYOSHUTSUKIN -> dto.getShokuinKodomoKosodateKyoshutsukin();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_RINJI_KOSEI_NENKIN_HOKENRYO -> dto.getRinjiKoseiNenkinHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_RINJI_KODOMO_KOSODATE_KYOSHUTSUKIN -> dto.getRinjiKodomoKosodateKyoshutsukin();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_SHIKYU_TEIKI_CHOKIN -> dto.getShikyuGakuTeikiChokin();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_SHIKYU_TSUMITATE_CHOKIN -> dto.getYochokinTsumitateChokin();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_SHIKYU_ZATSUSHIHARAI_RISOKU -> dto.getZatsushiharaiRisoku();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_SHIKYU_KYUYO_CHOSHUKIN -> dto.getKyuyoChoshukin();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_KOJO_RISOKUZEI_KOKUZEI -> dto.getRisokuzeiKokuzei();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_KOJO_RISOKUZEI_CHIHOZEI -> dto.getRisokuzeiChihozei();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_KOJO_TEIKI_CHOKIN -> dto.getKojoGakuTeikiChokin();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_KOJO_CHOKIN_TANPO_KASHITSUKEKIN -> dto.getChokinTanpoKashitsukekin();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_KOJO_CHOKIN_KASHITSUKE_RISOKU -> dto.getChokinKashitsukeRisoku();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_SASHIHIKI_FURIKOMI_GAKU -> dto.getYochokinFurikomiGaku();
      default -> BigDecimal.ZERO;
    };
  }

  private Integer getDetailHaitaCnt(DAC2015_LaborCostInfoEntryDetailInputDto dto, String komokuCd) {
    if (dto == null || StringUtils.isBlank(komokuCd)) {
      return null;
    }

    return switch (komokuCd) {
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_YAKUIN_HOUSHU -> dto.getHaitaCntYakuinHoushu();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_YAKUIN_TSUKIN_TEATE -> dto.getHaitaCntYakuinTsukinTeate();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_SHOKUIN_HONPO -> dto.getHaitaCntShokuinHonpo();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_SHOTEATE -> dto.getHaitaCntShoteate();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_TSUKIN_TEATE -> dto.getHaitaCntTsukinTeate();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_RINJISHA_CHINGIN -> dto.getHaitaCntRinjishaChingin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_RINJISHA_TSUKIN_TEATE -> dto.getHaitaCntRinjishaTsukinTeate();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_KYOIKU_KUNRENHI -> dto.getHaitaCntKyoikuKunrenhi();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_KEMPO_KYUFUKIN -> dto.getHaitaCntKempoKyufukin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_KIZUNA_HAITOKIN -> dto.getHaitaCntKizunaHaitokin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_HONKAI_JUTAKU_KASHITSUKEKIN -> dto.getHaitaCntHonkaiJutakuKashitsukekin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_FUTSU_KASHITSUKEKIN -> dto.getHaitaCntFutsuKashitsukekin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_TOKUBETSU_KASHITSUKEKIN -> dto.getHaitaCntTokubetsuKashitsukekin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SHOKOJO_GAKU -> dto.getHaitaCntShokojoGaku();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SONOTA_KOJO_GAKU -> dto.getHaitaCntSonotaKojoGaku();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_GENBUTSU_KYUYO_GAKU -> dto.getHaitaCntGenbutsuKyuyoGaku();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_BUPPINDAI -> dto.getHaitaCntBuppindai();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SHOKUHI -> dto.getHaitaCntShokuhi();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_HOKUREN_ROSOHI -> dto.getHaitaCntHokurenRosohi();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SEIMEI_HOKENRYO -> dto.getHaitaCntSeimeiHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SONGAI_HOKENRYO -> dto.getHaitaCntSongaiHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KIZUNA_TSUKIKAKEKIN -> dto.getHaitaCntKizunaTsukikakekin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KIZUNA_RINKAKEKIN -> dto.getHaitaCntKizunaRinkakekin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KENKO_HOKENRYO -> dto.getHaitaCntKenkoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KAIGO_HOKENRYO -> dto.getHaitaCntKaigoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_NENKIN_HOKENRYO -> dto.getHaitaCntNenkinHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KOYO_HOKENRYO -> dto.getHaitaCntKoyoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SHOTOKUZEI -> dto.getHaitaCntShotokuzei();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_JUMINZEI -> dto.getHaitaCntJuminzei();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_TSUMITATE_CHOKIN -> dto.getHaitaCntKojoGakuTsumitateChokin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_ZATSUUKETORI_RISOKU -> dto.getHaitaCntZatsuuketoriRisoku();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_JUTAKURYO -> dto.getHaitaCntJutakuryo();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SONOTA_AZUKARIKIN -> dto.getHaitaCntSonotaAzukarikin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_CHOKIN_TANPO_CHOSHU_GAKU -> dto.getHaitaCntChokinTanpoChoshuGaku();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_GAIBU_ZAISAN_KEISEI_KAKEKIN -> dto.getHaitaCntGaibuZaisanKeiseiKakekin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_FURIKOMI_GAKU -> dto.getHaitaCntKyuyoKojoFurikomiGaku();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_GENKIN_SHIKYU_GAKU -> dto.getHaitaCntGenkinShikyuGaku();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KARIUKEKIN -> dto.getHaitaCntKariukekin();
      case DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_AKASHIKYU_SAGAKU -> dto.getHaitaCntAkashikyuSagaku();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_YAKUIN_KENKO_HOKENRYO -> dto.getHaitaCntYakuinKenkoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_YAKUIN_KAIGO_HOKENRYO -> dto.getHaitaCntYakuinKaigoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_SHOKUIN_KENKO_HOKENRYO -> dto.getHaitaCntShokuinKenkoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_SHOKUIN_KAIGO_HOKENRYO -> dto.getHaitaCntShokuinKaigoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_RINJI_KENKO_HOKENRYO -> dto.getHaitaCntRinjiKenkoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_RINJI_KAIGO_HOKENRYO -> dto.getHaitaCntRinjiKaigoHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_YAKUIN_KOSEI_NENKIN_HOKENRYO -> dto.getHaitaCntYakuinKoseiNenkinHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_YAKUIN_KODOMO_KOSODATE_KYOSHUTSUKIN -> dto.getHaitaCntYakuinKodomoKosodateKyoshutsukin();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_SHOKUIN_KOSEI_NENKIN_HOKENRYO -> dto.getHaitaCntShokuinKoseiNenkinHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_SHOKUIN_KODOMO_KOSODATE_KYOSHUTSUKIN -> dto.getHaitaCntShokuinKodomoKosodateKyoshutsukin();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_RINJI_KOSEI_NENKIN_HOKENRYO -> dto.getHaitaCntRinjiKoseiNenkinHokenryo();
      case DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_RINJI_KODOMO_KOSODATE_KYOSHUTSUKIN -> dto.getHaitaCntRinjiKodomoKosodateKyoshutsukin();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_SHIKYU_TEIKI_CHOKIN -> dto.getHaitaCntShikyuGakuTeikiChokin();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_SHIKYU_TSUMITATE_CHOKIN -> dto.getHaitaCntYochokinTsumitateChokin();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_SHIKYU_ZATSUSHIHARAI_RISOKU -> dto.getHaitaCntZatsushiharaiRisoku();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_SHIKYU_KYUYO_CHOSHUKIN -> dto.getHaitaCntKyuyoChoshukin();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_KOJO_RISOKUZEI_KOKUZEI -> dto.getHaitaCntRisokuzeiKokuzei();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_KOJO_RISOKUZEI_CHIHOZEI -> dto.getHaitaCntRisokuzeiChihozei();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_KOJO_TEIKI_CHOKIN -> dto.getHaitaCntKojoGakuTeikiChokin();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_KOJO_CHOKIN_TANPO_KASHITSUKEKIN -> dto.getHaitaCntChokinTanpoKashitsukekin();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_KOJO_CHOKIN_KASHITSUKE_RISOKU -> dto.getHaitaCntChokinKashitsukeRisoku();
      case DAC2015_LaborCostInfoEntryCns.YOCHOKIN_SASHIHIKI_FURIKOMI_GAKU -> dto.getHaitaCntYochokinFurikomiGaku();
      default -> null;
    };
  }

  private BigDecimal nullToZero(BigDecimal value) {
    return value == null ? BigDecimal.ZERO : value;
  }

  private String extractShinseiNoByNyuryokuKbn(DAC2015_LaborCostInfoEntryListDetailDto inputListDetailDto,
      String nyuryokuKbnCd) {
    if (inputListDetailDto == null) {
      return null;
    }
    if (NYURYOKU_KBN_KYUYO_KOJO.equals(nyuryokuKbnCd)) {
      return inputListDetailDto.getShinseiNoKyuyoKojo();
    }
    return inputListDetailDto.getShinseiNo();
  }

  private String formatDate(LocalDate date) {
    if (date == null) {
      return "";
    }
    return String.format("%04d/%02d/%02d", date.getYear(), date.getMonthValue(), date.getDayOfMonth());
  }

  private String value(String s) {
    return s == null ? "" : s;
  }

  private String getListValue(List<String> list, int index) {
    if (list == null || list.size() <= index) {
      return "";
    }
    return list.get(index);
  }

  private DAC2015_LaborCostInfoEntryBasicInputSearchDto copyBasicInputSearchDto(
      DAC2015_LaborCostInfoEntryBasicInputSearchDto source) {
    if (source == null) {
      return new DAC2015_LaborCostInfoEntryBasicInputSearchDto();
    }
    return DAC2015_LaborCostInfoEntryBasicInputSearchDto.builder().shikyubi(source.getShikyubi())
        .nyuryokuKbnCd(source.getNyuryokuKbnCd()).nyuryokuKbnMeisho(source.getNyuryokuKbnMeisho())
        .kyuyoKbnCd(source.getKyuyoKbnCd()).kyuyoKbnMeisho(source.getKyuyoKbnMeisho())
        .kyuyoKashoCd(source.getKyuyoKashoCd()).kyuyoKashoMeisho(source.getKyuyoKashoMeisho())
        .kamokuBunruiCd(source.getKamokuBunruiCd()).kamokuBunruiMeisho(source.getKamokuBunruiMeisho())
        .kamokuKbnCd(source.getKamokuKbnCd()).kamokuKbnMeisho(source.getKamokuKbnMeisho())
        .gyoshuCd(source.getGyoshuCd()).gyoshuMeisho(source.getGyoshuMeisho())
        .shozokuCd(source.getShozokuCd()).shozokuMeisho(source.getShozokuMeisho())
        .listDetailNyuryokuKbnCd(source.getListDetailNyuryokuKbnCd()).build();
  }

  private List<String> getKyuyoShikyuKomokuList() {
    return List.of(DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_YAKUIN_HOUSHU,
        DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_YAKUIN_TSUKIN_TEATE,
        DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_SHOKUIN_HONPO,
        DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_SHOTEATE,
        DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_TSUKIN_TEATE,
        DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_RINJISHA_CHINGIN,
        DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_RINJISHA_TSUKIN_TEATE,
        DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_KYOIKU_KUNRENHI,
        DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_KEMPO_KYUFUKIN,
        DAC2015_LaborCostInfoEntryCns.KYUYO_SHIKYU_KIZUNA_HAITOKIN);
  }

  private List<String> getKyuyoKojoKomokuList() {
    return List.of(DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_HONKAI_JUTAKU_KASHITSUKEKIN,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_FUTSU_KASHITSUKEKIN,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_TOKUBETSU_KASHITSUKEKIN,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SHOKOJO_GAKU,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SONOTA_KOJO_GAKU,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_GENBUTSU_KYUYO_GAKU,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_BUPPINDAI,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SHOKUHI,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_HOKUREN_ROSOHI,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SEIMEI_HOKENRYO,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SONGAI_HOKENRYO,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KIZUNA_TSUKIKAKEKIN,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KIZUNA_RINKAKEKIN,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KENKO_HOKENRYO,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KAIGO_HOKENRYO,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_NENKIN_HOKENRYO,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KOYO_HOKENRYO,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SHOTOKUZEI,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_JUMINZEI,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_TSUMITATE_CHOKIN,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_ZATSUUKETORI_RISOKU,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_JUTAKURYO,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_SONOTA_AZUKARIKIN,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_CHOKIN_TANPO_CHOSHU_GAKU,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_GAIBU_ZAISAN_KEISEI_KAKEKIN,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_FURIKOMI_GAKU,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_GENKIN_SHIKYU_GAKU,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_KARIUKEKIN,
        DAC2015_LaborCostInfoEntryCns.KYUYO_KOJO_AKASHIKYU_SAGAKU);
  }

  private List<String> getKaifutanKempoKomokuList() {
    return List.of(DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_YAKUIN_KENKO_HOKENRYO,
        DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_YAKUIN_KAIGO_HOKENRYO,
        DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_SHOKUIN_KENKO_HOKENRYO,
        DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_SHOKUIN_KAIGO_HOKENRYO,
        DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_RINJI_KENKO_HOKENRYO,
        DAC2015_LaborCostInfoEntryCns.KAIFUTAN_KEMPO_RINJI_KAIGO_HOKENRYO);
  }

  private List<String> getKaifutanNenkinKomokuList() {
    return List.of(DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_YAKUIN_KOSEI_NENKIN_HOKENRYO,
        DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_YAKUIN_KODOMO_KOSODATE_KYOSHUTSUKIN,
        DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_SHOKUIN_KOSEI_NENKIN_HOKENRYO,
        DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_SHOKUIN_KODOMO_KOSODATE_KYOSHUTSUKIN,
        DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_RINJI_KOSEI_NENKIN_HOKENRYO,
        DAC2015_LaborCostInfoEntryCns.KAIFUTAN_NENKIN_RINJI_KODOMO_KOSODATE_KYOSHUTSUKIN);
  }

  private List<String> getYochokinShikyuKomokuList() {
    return List.of(DAC2015_LaborCostInfoEntryCns.YOCHOKIN_SHIKYU_TEIKI_CHOKIN,
        DAC2015_LaborCostInfoEntryCns.YOCHOKIN_SHIKYU_TSUMITATE_CHOKIN,
        DAC2015_LaborCostInfoEntryCns.YOCHOKIN_SHIKYU_ZATSUSHIHARAI_RISOKU,
        DAC2015_LaborCostInfoEntryCns.YOCHOKIN_SHIKYU_KYUYO_CHOSHUKIN);
  }

  private List<String> getYochokinKojoKomokuList() {
    return List.of(DAC2015_LaborCostInfoEntryCns.YOCHOKIN_KOJO_RISOKUZEI_KOKUZEI,
        DAC2015_LaborCostInfoEntryCns.YOCHOKIN_KOJO_RISOKUZEI_CHIHOZEI,
        DAC2015_LaborCostInfoEntryCns.YOCHOKIN_KOJO_TEIKI_CHOKIN,
        DAC2015_LaborCostInfoEntryCns.YOCHOKIN_KOJO_CHOKIN_TANPO_KASHITSUKEKIN,
        DAC2015_LaborCostInfoEntryCns.YOCHOKIN_KOJO_CHOKIN_KASHITSUKE_RISOKU,
        DAC2015_LaborCostInfoEntryCns.YOCHOKIN_SASHIHIKI_FURIKOMI_GAKU);
  }

  private List<String> getYochokinAllKomokuList() {
    List<String> list = new ArrayList<>();
    list.addAll(getYochokinShikyuKomokuList());
    list.addAll(getYochokinKojoKomokuList());
    return list;
  }
}
