/*
 * ClassName   DAC2015_LaborCostInfoEntryCtl
 *
 * Author      SCSK株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報入力 コントローラー
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2015;

import java.util.ArrayList;
import java.util.List;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.interceptor.Interceptors;
import jp.or.hokuren.aja.view.ajab212.AJAB212_ShoninRouteInputDto;
import jp.or.hokuren.aza.exception.AZA_ApplicationException;
import jp.or.hokuren.aza.log.annotation.AZA_ControllerLogInterceptor;
import jp.or.hokuren.aza.log.annotation.AZA_StartupLoggingInterceptor;
import jp.or.hokuren.aza.log.annotation.AZA_StartupMsgKanrNum;
import jp.or.hokuren.aza.session.AZA_LogonInfoDto;
import jp.or.hokuren.awa.common.util.AWA_ListItem;
import jp.or.hokuren.daa.common.utils.DAA_RirekiJohoDto;
import jp.or.hokuren.daa.common.utils.DAA_ShinseiJohoDto;
import jp.or.hokuren.daa.common.utils.DAA_ShinseiShoninJohoDto;
import jp.or.hokuren.daa.common.utils.DAA_WorkflowUtils;
import jp.or.hokuren.dac.def.DAC_MessageNoCns;
import jp.scsk.arvicio3.core.context.extension.NextPageScoped;
import jp.scsk.arvicio3.web.annotation.HandleBusinessValidation;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.StreamedContent;

/**
 * <pre>
 * 人件費情報入力 コントローラー
 * </pre>
 *
 * @author SCSK株式会社
 */
@Named("dac2015ctl")
@NextPageScoped
@HandleBusinessValidation
public class DAC2015_LaborCostInfoEntryCtl {

  private static final String APPROVAL_STATUS_UNSUBMITTED = "未申請";
  private static final String BTN_BASIC_REFERENCE = "basicInputReference";
  private static final String BTN_BASIC_KYUYO_KASHO_SEARCH = "basicInputKyuyoKashoSearch";
  private static final String BTN_BASIC_INDUSTRY_SEARCH = "basicInputIndustrySearch";
  private static final String BTN_BASIC_DEPT_SEARCH = "basicInputDeptSearch";
  private static final String BTN_DETAIL_REFERENCE = "detailInputReference";
  private static final String BTN_DETAIL_ASSIGNMENT_SUPPORT = "detailInputAssignmentSupport";
  private static final String BTN_CONFIRM_REFERENCE = "confirmInputReference";

  // 1. インジェクションクラス
  /** ログオン情報 */
  @Inject
  private AZA_LogonInfoDto logonInfoDto;

  /** 人件費情報入力ロジック */
  @Inject
  private DAC2015_LaborCostInfoEntryLgc entryLgc;

  /** 添付ファイルコントローラ */
  @Inject
  private DAA1134_AttachmentFileCtl attachmentFileCtl;

  /** 給与個所検索コントローラ */
  @Inject
  private DAA1133_SalarySectionSearchCtl salarySectionSearchCtl;

  /** 業種検索コントローラ */
  @Inject
  private DAA1108_IndustrySearchCtl industrySearchCtl;

  /** 所属検索コントローラ */
  @Inject
  private DAA1124_DeptSearchCtl deptSearchCtl;

  /** ワークフローUtils */
  @Inject
  private DAA_WorkflowUtils workflowUtils;

  // =========================================================
  // 2. フィールド
  // =========================================================

  @Getter
  @Setter
  private boolean initialized = false;

  @Getter
  @Setter
  private Integer unyoKanribi;

  @Getter
  @Setter
  private String buttonId;

  @Getter
  @Setter
  private Integer processModeDetail = null;

  @Getter
  @Setter
  private Integer processModeConfirm = null;

  @Getter
  @Setter
  private Integer processModeInquiry = null;

  @Getter
  @Setter
  private DAC2015_LaborCostInfoEntrySearchDto searchDto =
      new DAC2015_LaborCostInfoEntrySearchDto();

  @Getter
  @Setter
  private DAC2015_LaborCostInfoEntrySearchDto prevSearchDto =
      new DAC2015_LaborCostInfoEntrySearchDto();

  @Getter
  @Setter
  private DAC2015_LaborCostInfoEntrySearchDto initSearchDto =
      new DAC2015_LaborCostInfoEntrySearchDto();

  @Getter
  @Setter
  private List<DAC2015_LaborCostInfoEntryListDetailDto> listDetailDtoList = null;

  @Getter
  @Setter
  private List<DAC2015_LaborCostInfoEntryListDetailDto> listDetailSel = null;

  @Getter
  @Setter
  private DAC2015_LaborCostInfoEntryBasicInputDto basicInputDto =
      new DAC2015_LaborCostInfoEntryBasicInputDto();

  @Getter
  @Setter
  private DAC2015_LaborCostInfoEntryBasicInputDto beforeBasicInputDto =
      new DAC2015_LaborCostInfoEntryBasicInputDto();

  @Getter
  @Setter
  private DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputSearchDto =
      new DAC2015_LaborCostInfoEntryBasicInputSearchDto();

  @Getter
  @Setter
  private DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputPrevSearchDto =
      new DAC2015_LaborCostInfoEntryBasicInputSearchDto();

  @Getter
  @Setter
  private DAC2015_LaborCostInfoEntryBasicInputSearchDto basicInputInitSearchDto =
      new DAC2015_LaborCostInfoEntryBasicInputSearchDto();

  @Getter
  @Setter
  private List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> basicInputListDetailDtoList = null;

  @Getter
  @Setter
  private List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> basicInputListDetailSel = null;

  @Getter
  @Setter
  private DAC2015_LaborCostInfoEntryBasicInputListDetailDto basicInputDisplayJoho = null;

  @Getter
  @Setter
  private DAC2015_LaborCostInfoEntryDetailInputDto detailInputDto =
      new DAC2015_LaborCostInfoEntryDetailInputDto();

  @Getter
  @Setter
  private DAC2015_LaborCostInfoEntryDetailInputDto beforeDetailInputDto =
      new DAC2015_LaborCostInfoEntryDetailInputDto();

  @Getter
  @Setter
  private DAC2015_LaborCostInfoRendoListCsvDto csvDto =
      new DAC2015_LaborCostInfoRendoListCsvDto();

  @Getter
  @Setter
  private DAC2015_LaborCostInfoEntryBasicInputDto confirmDto =
      new DAC2015_LaborCostInfoEntryBasicInputDto();

  @Getter
  @Setter
  private List<DAC2015_LaborCostInfoEntryBasicInputListDetailDto> confirmListDetailDtoList = null;

  @Getter
  @Setter
  private DAC2015_LaborCostInfoEntryBasicInputListDetailDto confirmListDetailSel =
      new DAC2015_LaborCostInfoEntryBasicInputListDetailDto();

  @Getter
  @Setter
  private DAC2015_LaborCostInfoEntryDetailInputDto inquiryConfirmDetailDto =
      new DAC2015_LaborCostInfoEntryDetailInputDto();

  @Getter
  @Setter
  private String inputOperationMessage = null;

  @Getter
  @Setter
  private String basicInputOperationMessage = null;

  @Getter
  @Setter
  private String detailInputOperationMessage = null;

  @Getter
  @Setter
  private String confirmOperationMessage = null;

  @Getter
  @Setter
  private String confirmOperationMessageApply = null;

  @Getter
  @Setter
  private String confirmOperationMessageInquiry = null;

  @Getter
  @Setter
  private String confirmOperationMessageWithdraw = null;

  @Getter
  @Setter
  private String inquiryConfirmOperationMessage = null;

  @Getter
  @Setter
  private Boolean isApprovalInfoPanelDetailInputClosed = false;

  @Getter
  @Setter
  private Boolean isApprovalInfoPanelConfirmClosed = false;

  @Getter
  @Setter
  private List<String> screenInfoList = null;

  @Getter
  @Setter
  private Boolean confirmDialogDisplayFlg = false;

  @Getter
  @Setter
  private boolean isConditionPanelInputClosed = false;

  @Getter
  @Setter
  private boolean isConditionPanelBasicInputClosed = false;

  @Getter
  @Setter
  private String approvalStatus = null;

  @Getter
  @Setter
  private List<AWA_ListItem> basicInputNyuryokuKbnList = null;

  @Getter
  @Setter
  private List<AWA_ListItem> nyuryokuKbnList = null;

  @Getter
  @Setter
  private List<AWA_ListItem> kyuyoKbnList = null;

  @Getter
  @Setter
  private List<AWA_ListItem> kamokuBunruiList = null;

  @Getter
  @Setter
  private List<AWA_ListItem> kamokuKbnList = null;

  @Getter
  @Setter
  private boolean isKyuyoShikyuPanelRendered = false;

  @Getter
  @Setter
  private boolean isKyuyoKojoPanelRendered = false;

  @Getter
  @Setter
  private boolean isKaifutanKempoPanelRendered = false;

  @Getter
  @Setter
  private boolean isKaifutanNenkinPanelRendered = false;

  @Getter
  @Setter
  private boolean isYochokinPanelRendered = false;

  @Getter
  @Setter
  private boolean isShiharaisakiRendered = false;

  @Getter
  @Setter
  private DAA_ShinseiJohoDto shinseiJohoDto = null;

  @Getter
  @Setter
  private DAA_RirekiJohoDto rirekiJohoDto = null;

  @Getter
  @Setter
  private DAA_ShinseiShoninJohoDto confirmShinseiShoninDto = new DAA_ShinseiShoninJohoDto();

  @Getter
  @Setter
  private AJAB212_ShoninRouteInputDto confirmShinseiShoninRouteDto = new AJAB212_ShoninRouteInputDto();

  // =========================================================
  // 3. アクションメソッド
  // =========================================================

  @AZA_StartupMsgKanrNum(DAC_MessageNoCns.DAC80000I)
  @Interceptors({AZA_StartupLoggingInterceptor.class, AZA_ControllerLogInterceptor.class})
  public void init() throws AZA_ApplicationException {
    // 1. 条件を判定する。
    if (initialized) {
      return;
    }
    // 2. コントローラ.searchDtoを初期化する。
    this.searchDto = new DAC2015_LaborCostInfoEntrySearchDto();
    // 3. コントローラ.prevSearchDtoを初期化する。
    this.prevSearchDto = new DAC2015_LaborCostInfoEntrySearchDto();
    // 4. コントローラ.initSearchDtoを初期化する。
    this.initSearchDto = new DAC2015_LaborCostInfoEntrySearchDto();
    // 5. コントローラ.listDetailDtoListを初期化する。
    this.listDetailDtoList = new ArrayList<>();
    // 6. コントローラ.listDetailSelを初期化する。
    this.listDetailSel = new ArrayList<>();
    // 7. 処理を実行する。
    this.searchDto.setShoninJokyoMishusei(true);
    // 8. 処理を実行する。
    this.searchDto.setShoninJokyoSashimodoshi(true);
    // 9. 処理を実行する。
    this.searchDto.setShoninJokyoShitagakiHozon(true);
    // 10. 処理を実行する。
    this.searchDto.setShoninJokyoShoninIraichu(true);
    // 11. 処理を実行する。
    this.searchDto.setShoninJokyoShoninzumi(false);
    // 12. ロジック#initMainを実行し、戻り値をローカル変数.resultに設定する。
    DAC2015_InitMainResponseRecord result = entryLgc.initMain();
    // 13. 処理を実行する。
    this.searchDto.setTaishoNengetsuFrom(result.unyoKanribi() / 100);
    // 14. 処理を実行する。
    this.searchDto.setTaishoNengetsuTo(result.unyoKanribi() / 100);
    // 15. コントローラ.initSearchDtoに値を設定する。
    this.initSearchDto = copySearchDto(this.searchDto);
    // 16. コントローラ.unyoKanribiに値を設定する。
    this.unyoKanribi = result.unyoKanribi();
    // 17. コントローラ.nyuryokuKbnListに値を設定する。
    this.nyuryokuKbnList = result.nyuryokuKbnObjectList();
    // 18. コントローラ.kyuyoKbnListに値を設定する。
    this.kyuyoKbnList = result.kyuyoKbnObjectList();
    // 19. コントローラ.kamokuBunruiListに値を設定する。
    this.kamokuBunruiList = result.kamokuBunruiObjectList();
    // 20. コントローラ.kamokuKbnListに値を設定する。
    this.kamokuKbnList = result.kamokuKbnObjectList();
    // 21. コントローラ.screenInfoListに値を設定する。
    this.screenInfoList = result.operationMessageList();
    // 22. コントローラ.inputOperationMessageに値を設定する。
    this.inputOperationMessage = getMessageByIndex(this.screenInfoList, 0);
    // 23. コントローラ.basicInputOperationMessageに値を設定する。
    this.basicInputOperationMessage = getMessageByIndex(this.screenInfoList, 1);
    // 24. コントローラ.detailInputOperationMessageに値を設定する。
    this.detailInputOperationMessage = getMessageByIndex(this.screenInfoList, 2);
    // 25. コントローラ.inquiryConfirmOperationMessageに値を設定する。
    this.inquiryConfirmOperationMessage = getMessageByIndex(this.screenInfoList, 3);
    // 26. コントローラ.confirmOperationMessageApplyに値を設定する。
    this.confirmOperationMessageApply = getMessageByIndex(this.screenInfoList, 4);
    // 27. コントローラ.confirmOperationMessageInquiryに値を設定する。
    this.confirmOperationMessageInquiry = getMessageByIndex(this.screenInfoList, 5);
    // 28. コントローラ.confirmOperationMessageWithdrawに値を設定する。
    this.confirmOperationMessageWithdraw = getMessageByIndex(this.screenInfoList, 6);
    // 29. コントローラ.screenInfoListを初期化する。
    this.screenInfoList = new ArrayList<>();
    // 30. コントローラ.screenInfoListに要素を追加する。
    this.screenInfoList.add(DAC2015_LaborCostInfoEntryCns.GAMEN_SENI_SEARCH);
    // 31. コントローラ.initializedに値を設定する。
    this.initialized = true;
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void inputSearch() throws AZA_ApplicationException {
    // 1. コントローラ.listDetailDtoListを初期化する。
    this.listDetailDtoList = new ArrayList<>();
    // 2. ロジック#searchInputMainを実行し、戻り値をコントローラ.listDetailDtoListに設定する。
    this.listDetailDtoList = entryLgc.searchInputMain(searchDto);
    // 3. コントローラ.isConditionPanelInputClosedに値を設定する。
    this.isConditionPanelInputClosed = true;
    // 4. コントローラ.prevSearchDtoに値を設定する。
    this.prevSearchDto = copySearchDto(this.searchDto);
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void inputClear() throws AZA_ApplicationException {
    // 1. コントローラ.searchDtoに値を設定する。
    this.searchDto = copySearchDto(this.initSearchDto);
    // 2. コントローラ.listDetailDtoListを初期化する。
    this.listDetailDtoList = new ArrayList<>();
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void inputNext() throws AZA_ApplicationException {
    // 1. 処理を実行する。
    DAC2015_NextInputMainResponseRecord result =
        entryLgc.nextInputMain(listDetailSel, nyuryokuKbnList);
    // 2. コントローラ.basicInputDtoに値を設定する。
    this.basicInputDto = result.basicInputDto();
    // 3. コントローラ.basicInputSearchDtoに値を設定する。
    this.basicInputSearchDto = result.basicInputSearchDto();
    // 4. コントローラ.basicInputInitSearchDtoに値を設定する。
    this.basicInputInitSearchDto = copyBasicInputSearchDto(this.basicInputSearchDto);
    // 5. コントローラ.basicInputNyuryokuKbnListに値を設定する。
    this.basicInputNyuryokuKbnList = result.basicInputNyuryokuKbnObjectList();
    // 6. コントローラ.approvalStatusに値を設定する。
    this.approvalStatus = firstSelectedInput().getShoninJokyoKbn();
    // 7. コントローラ.isConditionPanelBasicInputClosedに値を設定する。
    this.isConditionPanelBasicInputClosed = false;
    // 8. コントローラ.screenInfoListに要素を追加する。
    this.screenInfoList.add(DAC2015_LaborCostInfoEntryCns.GAMEN_SENI_INPUT);
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void inputInquiry() throws AZA_ApplicationException {
    // 1. 処理を実行する。
    DAC2015_InquiryInputMainResponseRecord result =
        entryLgc.inquiryInputMain(listDetailSel);
    // 2. コントローラ.basicInputDtoに値を設定する。
    this.basicInputDto = result.basicInputDto();
    // 3. コントローラ.confirmListDetailDtoListに値を設定する。
    this.confirmListDetailDtoList = result.basicInputListDetailDtoList();
    // 4. コントローラ.basicInputListDetailDtoListに値を設定する。
    this.basicInputListDetailDtoList = result.basicInputListDetailDtoList();
    // 5. コントローラ.confirmShinseiShoninDtoに値を設定する。
    this.confirmShinseiShoninDto = result.shinseiShoninJohoDto();
    // 6. コントローラ.shinseiJohoDtoに値を設定する。
    this.shinseiJohoDto = firstOrDefaultShinsei(this.confirmShinseiShoninDto);
    // 7. コントローラ.rirekiJohoDtoに値を設定する。
    this.rirekiJohoDto = firstOrDefaultRireki(this.confirmShinseiShoninDto);
    // 8. コントローラ.approvalStatusに値を設定する。
    this.approvalStatus = firstSelectedInput().getShoninJokyoKbn();
    // 9. コントローラ.isApprovalInfoPanelConfirmClosedに値を設定する。
    this.isApprovalInfoPanelConfirmClosed = !APPROVAL_STATUS_UNSUBMITTED.equals(this.approvalStatus);
    // 10. コントローラ.processModeConfirmに値を設定する。
    this.processModeConfirm = DAC2015_LaborCostInfoEntryCns.PROCESS_MODE_INQUIRY;
    // 11. コントローラ.confirmOperationMessageに値を設定する。
    this.confirmOperationMessage = this.confirmOperationMessageInquiry;
    // 12. コントローラ.screenInfoListに要素を追加する。
    this.screenInfoList.add(DAC2015_LaborCostInfoEntryCns.GAMEN_SENI_CONFIRM);
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void inputWithdraw() throws AZA_ApplicationException {
    // 1. 処理を実行する。
    DAC2015_WithdrawInputMainResponseRecord result =
        entryLgc.withdrawInputMain(listDetailSel);
    // 2. コントローラ.basicInputDtoに値を設定する。
    this.basicInputDto = result.basicInputDto();
    // 3. コントローラ.confirmListDetailDtoListに値を設定する。
    this.confirmListDetailDtoList = result.basicInputListDetailDtoList();
    // 4. コントローラ.basicInputListDetailDtoListに値を設定する。
    this.basicInputListDetailDtoList = result.basicInputListDetailDtoList();
    // 5. コントローラ.confirmShinseiShoninRouteDtoに値を設定する。
    this.confirmShinseiShoninRouteDto = result.shoninRouteInputDto();
    // 6. コントローラ.confirmShinseiShoninDtoに値を設定する。
    this.confirmShinseiShoninDto = result.shinseiShoninJohoDto();
    // 7. コントローラ.shinseiJohoDtoに値を設定する。
    this.shinseiJohoDto = firstOrDefaultShinsei(this.confirmShinseiShoninDto);
    // 8. コントローラ.rirekiJohoDtoに値を設定する。
    this.rirekiJohoDto = firstOrDefaultRireki(this.confirmShinseiShoninDto);
    // 9. コントローラ.approvalStatusに値を設定する。
    this.approvalStatus = firstSelectedInput().getShoninJokyoKbn();
    // 10. コントローラ.isApprovalInfoPanelConfirmClosedに値を設定する。
    this.isApprovalInfoPanelConfirmClosed = !APPROVAL_STATUS_UNSUBMITTED.equals(this.approvalStatus);
    // 11. コントローラ.processModeConfirmに値を設定する。
    this.processModeConfirm = DAC2015_LaborCostInfoEntryCns.BUTTON_TYPE_WITHDRAW;
    // 12. コントローラ.confirmOperationMessageに値を設定する。
    this.confirmOperationMessage = this.confirmOperationMessageWithdraw;
    // 13. コントローラ.screenInfoListに要素を追加する。
    this.screenInfoList.add(DAC2015_LaborCostInfoEntryCns.GAMEN_SENI_CONFIRM);
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void basicInputReference() throws AZA_ApplicationException {
    // 1. コントローラ.buttonIdに値を設定する。
    this.buttonId = BTN_BASIC_REFERENCE;
    // 2. attachmentFileCtlをオープンする。
    attachmentFileCtl.open();
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void basicInputKyuyoKashoSearch() throws AZA_ApplicationException {
    // 1. コントローラ.buttonIdに値を設定する。
    this.buttonId = BTN_BASIC_KYUYO_KASHO_SEARCH;
    // 2. salarySectionSearchCtlをオープンする。
    salarySectionSearchCtl.open();
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void basicInputIndustrySearch() throws AZA_ApplicationException {
    // 1. コントローラ.buttonIdに値を設定する。
    this.buttonId = BTN_BASIC_INDUSTRY_SEARCH;
    // 2. industrySearchCtlをオープンする。
    industrySearchCtl.open();
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void basicInputDeptSearch() throws AZA_ApplicationException {
    // 1. コントローラ.buttonIdに値を設定する。
    this.buttonId = BTN_BASIC_DEPT_SEARCH;
    // 2. deptSearchCtlをオープンする。
    deptSearchCtl.open();
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void basicInputRegist() throws AZA_ApplicationException {
    // 1. コントローラ.detailInputDtoを初期化する。
    this.detailInputDto = new DAC2015_LaborCostInfoEntryDetailInputDto();
    // 2. 処理を実行する。
    DAC2015_RegistBasicInputMainResponseRecord result =
        entryLgc.registBasicInputMain(basicInputSearchDto, listDetailSel != null && !listDetailSel.isEmpty() ? listDetailSel.get(0) : new DAC2015_LaborCostInfoEntryListDetailDto());
    // 3. コントローラ.basicInputDisplayJohoに値を設定する。
    this.basicInputDisplayJoho = result.basicInputListDetailDto();
    // 4. コントローラ.detailInputDtoに値を設定する。
    this.detailInputDto = result.detailInputDto();
    // 5. コントローラ.beforeDetailInputDtoに値を設定する。
    this.beforeDetailInputDto = result.detailInputDto();
    // 6. コントローラ.confirmShinseiShoninRouteDtoに値を設定する。
    this.confirmShinseiShoninRouteDto = result.shoninRouteInputDto();
    // 7. コントローラ.confirmShinseiShoninDtoに値を設定する。
    this.confirmShinseiShoninDto = result.shinseiShoninJohoDto();
    // 8. コントローラ.shinseiJohoDtoに値を設定する。
    this.shinseiJohoDto = firstOrDefaultShinsei(this.confirmShinseiShoninDto);
    // 9. コントローラ.rirekiJohoDtoに値を設定する。
    this.rirekiJohoDto = firstOrDefaultRireki(this.confirmShinseiShoninDto);
    // 10. コントローラ.isApprovalInfoPanelDetailInputClosedに値を設定する。
    this.isApprovalInfoPanelDetailInputClosed = !APPROVAL_STATUS_UNSUBMITTED.equals(this.approvalStatus);
    // 11. コントローラ.processModeDetailに値を設定する。
    this.processModeDetail = DAC2015_LaborCostInfoEntryCns.PROCESS_MODE_REGIST;
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void basicInputSearch() throws AZA_ApplicationException {
    // 1. コントローラ.basicInputListDetailDtoListを初期化する。
    this.basicInputListDetailDtoList = new ArrayList<>();
    // 2. 処理を実行する。
    DAC2015_SearchBasicInputMainResponseRecord result =
        entryLgc.searchBasicInputMain(basicInputSearchDto);
    // 3. コントローラ.basicInputListDetailDtoListに値を設定する。
    this.basicInputListDetailDtoList = result.basicInputListDetailDtoList();
    // 4. 処理を実行する。
    this.basicInputSearchDto.setKyuyoKashoMeisho(result.kyuyoKashoMeisho());
    // 5. 処理を実行する。
    this.basicInputSearchDto.setGyoshuMeisho(result.gyoshuMeisho());
    // 6. 処理を実行する。
    this.basicInputSearchDto.setShozokuMeisho(result.shozokuMeisho());
    // 7. コントローラ.isConditionPanelBasicInputClosedに値を設定する。
    this.isConditionPanelBasicInputClosed = true;
    // 8. コントローラ.basicInputPrevSearchDtoに値を設定する。
    this.basicInputPrevSearchDto = copyBasicInputSearchDto(this.basicInputSearchDto);
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void basicInputClear() throws AZA_ApplicationException {
    // 1. コントローラ.basicInputSearchDtoに値を設定する。
    this.basicInputSearchDto = copyBasicInputSearchDto(this.basicInputInitSearchDto);
    // 2. コントローラ.isConditionPanelBasicInputClosedに値を設定する。
    this.isConditionPanelBasicInputClosed = false;
    // 3. コントローラ.basicInputListDetailDtoListを初期化する。
    this.basicInputListDetailDtoList = new ArrayList<>();
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void basicInputListShow() throws AZA_ApplicationException {
    List<String> meishoList = entryLgc.listShowBasicInputMain(basicInputSearchDto);
    if (meishoList != null && meishoList.size() >= 3) {
      this.basicInputSearchDto.setKyuyoKashoMeisho(meishoList.get(0));
      this.basicInputSearchDto.setGyoshuMeisho(meishoList.get(1));
      this.basicInputSearchDto.setShozokuMeisho(meishoList.get(2));
    }
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void basicInputPreview() throws AZA_ApplicationException {
    // 1. コントローラ.approvalStatusに値を設定する。
    this.approvalStatus = null;
    // 2. コントローラ.searchDtoに値を設定する。
    this.searchDto = copySearchDto(this.prevSearchDto);
    // 3. 処理を実行する。
    this.screenInfoList.remove(DAC2015_LaborCostInfoEntryCns.GAMEN_SENI_INPUT);
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void basicInputModify() throws AZA_ApplicationException {
    // 1. 処理を実行する。
    DAC2015_ModifyBasicInputMainResponseRecord result =
        entryLgc.modifyBasicInputMain(
            basicInputListDetailSel,
            listDetailSel != null && !listDetailSel.isEmpty() ? listDetailSel.get(0) : new DAC2015_LaborCostInfoEntryListDetailDto());
    // 2. コントローラ.detailInputDtoに値を設定する。
    this.detailInputDto = result.detailInputDto();
    // 3. コントローラ.beforeDetailInputDtoに値を設定する。
    this.beforeDetailInputDto = result.detailInputDto();
    // 4. コントローラ.basicInputDisplayJohoに値を設定する。
    this.basicInputDisplayJoho = firstSelectedBasicInput();
    // 5. コントローラ.confirmShinseiShoninDtoに値を設定する。
    this.confirmShinseiShoninDto = result.shinseiShoninJohoDto();
    // 6. コントローラ.shinseiJohoDtoに値を設定する。
    this.shinseiJohoDto = firstOrDefaultShinsei(this.confirmShinseiShoninDto);
    // 7. コントローラ.rirekiJohoDtoに値を設定する。
    this.rirekiJohoDto = firstOrDefaultRireki(this.confirmShinseiShoninDto);
    // 8. コントローラ.isApprovalInfoPanelDetailInputClosedに値を設定する。
    this.isApprovalInfoPanelDetailInputClosed = !APPROVAL_STATUS_UNSUBMITTED.equals(this.approvalStatus);
    // 9. コントローラ.processModeDetailに値を設定する。
    this.processModeDetail = DAC2015_LaborCostInfoEntryCns.PROCESS_MODE_MODIFY;
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public StreamedContent basicInputExportCsv() throws AZA_ApplicationException {
    return entryLgc.exportCsvBasicInputMain(basicInputSearchDto, basicInputListDetailDtoList);
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void basicInputSaveDraft() throws AZA_ApplicationException {
    // 1. 処理を実行する。
    DAC2015_LaborCostInfoEntryListDetailDto listDetail =
        listDetailSel != null && !listDetailSel.isEmpty()
            ? listDetailSel.get(0) : new DAC2015_LaborCostInfoEntryListDetailDto();
    this.listDetailDtoList = entryLgc.saveDraftBasicInputMain(
      listDetail, basicInputDto, prevSearchDto, beforeBasicInputDto);
    // 2. コントローラ.approvalStatusに値を設定する。
    this.approvalStatus = null;
    // 3. コントローラ.searchDtoに値を設定する。
    this.searchDto = copySearchDto(this.prevSearchDto);
    // 4. 処理を実行する。
    this.screenInfoList.remove(DAC2015_LaborCostInfoEntryCns.GAMEN_SENI_INPUT);
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void basicInputDelete() throws AZA_ApplicationException {
    // 1. 処理を実行する。
    DAC2015_DeleteBasicInputMainResponseRecord result =
        entryLgc.deleteBasicInputMain(
            basicInputListDetailSel,
            listDetailSel != null && !listDetailSel.isEmpty() ? listDetailSel.get(0) : new DAC2015_LaborCostInfoEntryListDetailDto());
    // 2. コントローラ.basicInputDisplayJohoに値を設定する。
    this.basicInputDisplayJoho = firstSelectedBasicInput();
    // 3. コントローラ.inquiryConfirmDetailDtoに値を設定する。
    this.inquiryConfirmDetailDto = result.detailInputDto();
    // 4. コントローラ.confirmShinseiShoninRouteDtoに値を設定する。
    this.confirmShinseiShoninRouteDto = result.shoninRouteInputDto();
    // 5. 明細パネル表示制御を適用する。
    applyDetailPanelMatrix(this.basicInputDisplayJoho);
    // 6. コントローラ.processModeInquiryに値を設定する。
    this.processModeInquiry = DAC2015_LaborCostInfoEntryCns.PROCESS_MODE_DELETE;
    // 7. コントローラ.screenInfoListに要素を追加する。
    this.screenInfoList.add(DAC2015_LaborCostInfoEntryCns.GAMEN_SENI_CONFIRM);
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void basicInputInquiry() throws AZA_ApplicationException {
    // 1. 処理を実行する。
    DAC2015_InquiryBasicInputMainResponseRecord result =
        entryLgc.inquiryBasicInputMain(
            basicInputListDetailSel,
            listDetailSel != null && !listDetailSel.isEmpty() ? listDetailSel.get(0) : new DAC2015_LaborCostInfoEntryListDetailDto());
    // 2. コントローラ.inquiryConfirmDetailDtoに値を設定する。
    this.inquiryConfirmDetailDto = result.detailInputDto();
    // 3. コントローラ.basicInputDisplayJohoに値を設定する。
    this.basicInputDisplayJoho = firstSelectedBasicInput();
    // 4. 明細パネル表示制御を適用する。
    applyDetailPanelMatrix(this.basicInputDisplayJoho);
    // 5. コントローラ.confirmShinseiShoninDtoに値を設定する。
    this.confirmShinseiShoninDto = result.shinseiShoninJohoDto();
    // 6. コントローラ.processModeInquiryに値を設定する。
    this.processModeInquiry = DAC2015_LaborCostInfoEntryCns.PROCESS_MODE_INQUIRY;
    // 7. コントローラ.screenInfoListに要素を追加する。
    this.screenInfoList.add(DAC2015_LaborCostInfoEntryCns.GAMEN_SENI_CONFIRM);
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void basicInputConfirm() throws AZA_ApplicationException {
    // 1. 処理を実行する。
    DAC2015_ConfirmBasicInputMainResponseRecord result =
        entryLgc.confirmBasicInputMain(
            basicInputSearchDto,
            listDetailSel != null && !listDetailSel.isEmpty() ? listDetailSel.get(0) : new DAC2015_LaborCostInfoEntryListDetailDto());
    // 2. コントローラ.confirmListDetailDtoListに値を設定する。
    this.confirmListDetailDtoList = result.basicInputListDetailDtoList();
    // 3. コントローラ.confirmShinseiShoninDtoに値を設定する。
    this.confirmShinseiShoninDto = result.shinseiShoninJohoDto();
    // 4. コントローラ.confirmShinseiShoninRouteDtoに値を設定する。
    this.confirmShinseiShoninRouteDto = result.shoninRouteInputDto();
    // 5. コントローラ.shinseiJohoDtoに値を設定する。
    this.shinseiJohoDto = firstOrDefaultShinsei(this.confirmShinseiShoninDto);
    // 6. コントローラ.rirekiJohoDtoに値を設定する。
    this.rirekiJohoDto = firstOrDefaultRireki(this.confirmShinseiShoninDto);
    // 7. コントローラ.isApprovalInfoPanelConfirmClosedに値を設定する。
    this.isApprovalInfoPanelConfirmClosed = !APPROVAL_STATUS_UNSUBMITTED.equals(this.approvalStatus);
    // 8. コントローラ.confirmOperationMessageに値を設定する。
    this.confirmOperationMessage = this.confirmOperationMessageApply;
    // 9. コントローラ.processModeConfirmに値を設定する。
    this.processModeConfirm = DAC2015_LaborCostInfoEntryCns.PROCESS_MODE_CONFIRM;
    // 10. コントローラ.screenInfoListに要素を追加する。
    this.screenInfoList.add(DAC2015_LaborCostInfoEntryCns.GAMEN_SENI_CONFIRM);
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void detailInputReference() throws AZA_ApplicationException {
    // 1. コントローラ.buttonIdに値を設定する。
    this.buttonId = BTN_DETAIL_REFERENCE;
    // 2. attachmentFileCtlをオープンする。
    attachmentFileCtl.open();
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void detailInputPreview() throws AZA_ApplicationException {
    // 1. コントローラ.processModeDetailに値を設定する。
    this.processModeDetail = null;
    // 2. 条件を判定する。
    if (this.basicInputListDetailDtoList != null && !this.basicInputListDetailDtoList.isEmpty()) {
      // 3. コントローラ.basicInputSearchDtoに値を設定する。
      this.basicInputSearchDto = copyBasicInputSearchDto(this.basicInputPrevSearchDto);
    }
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void detailInputSaveDraft() throws AZA_ApplicationException {
    // 1. ロジック#saveDraftDetailInputMainを実行し、戻り値をコントローラ.basicInputDtoに設定する。
    this.basicInputDto = entryLgc.saveDraftDetailInputMain(
        processModeDetail,
        firstSelectedInput(),
        basicInputDto,
        beforeBasicInputDto,
        basicInputDisplayJoho,
        detailInputDto,
        beforeDetailInputDto);
    // 2. コントローラ.beforeBasicInputDtoに値を設定する。
    this.beforeBasicInputDto = this.basicInputDto;
    // 3. コントローラ.processModeDetailに値を設定する。
    this.processModeDetail = null;
    // 4. 条件を判定する。
    if (DAC2015_LaborCostInfoEntryCns.PROCESS_MODE_MODIFY.equals(processModeDetail)) {
      this.basicInputSearchDto = copyBasicInputSearchDto(this.basicInputPrevSearchDto);
    }
    // 5. ロジック#searchBasicInputMainを実行し、戻り値をローカル変数.resultに設定する。
    DAC2015_SearchBasicInputMainResponseRecord result = entryLgc.searchBasicInputMain(this.basicInputSearchDto);
    // 6. コントローラ.basicInputListDetailDtoListに値を設定する。
    this.basicInputListDetailDtoList = result.basicInputListDetailDtoList();
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void confirmPreview() throws AZA_ApplicationException {
    // 1. 条件を判定する。
    if (DAC2015_LaborCostInfoEntryCns.PROCESS_MODE_CONFIRM.equals(this.processModeConfirm)) {
      // 2. コントローラ.processModeConfirmに値を設定する。
      this.processModeConfirm = null;
      // 3. 条件を判定する。
      if (this.basicInputListDetailDtoList != null && !this.basicInputListDetailDtoList.isEmpty()) {
        this.basicInputSearchDto = copyBasicInputSearchDto(this.basicInputPrevSearchDto);
      }
      // 4. 処理を実行する。
      this.screenInfoList.remove(DAC2015_LaborCostInfoEntryCns.GAMEN_SENI_CONFIRM);
      return;
    }
    // 5. コントローラ.processModeConfirmに値を設定する。
    this.processModeConfirm = null;
    // 6. コントローラ.approvalStatusに値を設定する。
    this.approvalStatus = null;
    // 7. 条件を判定する。
    if (this.listDetailDtoList != null && !this.listDetailDtoList.isEmpty()) {
      this.searchDto = copySearchDto(this.prevSearchDto);
    }
    // 8. 処理を実行する。
    this.screenInfoList.remove(DAC2015_LaborCostInfoEntryCns.GAMEN_SENI_CONFIRM);
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void confirmApply() throws AZA_ApplicationException {
    // 1. ロジック#applyConfirmMainを実行し、戻り値をコントローラ.listDetailDtoListに設定する。
    this.listDetailDtoList = entryLgc.applyConfirmMain(
        copySearchDto(this.prevSearchDto),
        firstSelectedInput(),
        this.confirmListDetailDtoList,
        this.basicInputDto,
        this.beforeBasicInputDto,
        this.confirmShinseiShoninRouteDto);
    // 2. コントローラ.processModeConfirmに値を設定する。
    this.processModeConfirm = null;
    // 3. コントローラ.approvalStatusに値を設定する。
    this.approvalStatus = null;
    // 4. コントローラ.screenInfoListを初期化する。
    this.screenInfoList = new ArrayList<>();
    // 5. コントローラ.screenInfoListに要素を追加する。
    this.screenInfoList.add(DAC2015_LaborCostInfoEntryCns.GAMEN_SENI_SEARCH);
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void confirmInquiry() throws AZA_ApplicationException {
    // 1. ロジック#inquiryConfirmMainを実行し、戻り値をコントローラ.inquiryConfirmDetailDtoに設定する。
    this.inquiryConfirmDetailDto = entryLgc.inquiryConfirmMain(confirmListDetailSel);
    // 2. コントローラ.basicInputDisplayJohoに値を設定する。
    this.basicInputDisplayJoho = this.confirmListDetailSel;
    // 3. 明細パネル表示制御を適用する。
    applyDetailPanelMatrix(this.basicInputDisplayJoho);
    // 4. コントローラ.processModeInquiryに値を設定する。
    this.processModeInquiry = DAC2015_LaborCostInfoEntryCns.PROCESS_MODE_INQUIRY;
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void confirmReference() throws AZA_ApplicationException {
    // 1. コントローラ.buttonIdに値を設定する。
    this.buttonId = BTN_CONFIRM_REFERENCE;
    // 2. attachmentFileCtlをオープンする。
    attachmentFileCtl.open();
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void confirmApprovalRoute() throws AZA_ApplicationException {
    // 1. コントローラ.buttonIdに値を設定する。
    this.buttonId = BTN_DETAIL_ASSIGNMENT_SUPPORT;
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void confirmWithdraw() throws AZA_ApplicationException {
    // 1. ロジック#withdrawConfirmMainを実行し、戻り値をコントローラ.listDetailDtoListに設定する。
    this.listDetailDtoList = entryLgc.withdrawConfirmMain(copySearchDto(this.prevSearchDto), firstSelectedInput());
    // 2. コントローラ.processModeConfirmに値を設定する。
    this.processModeConfirm = null;
    // 3. コントローラ.approvalStatusに値を設定する。
    this.approvalStatus = null;
    // 4. コントローラ.screenInfoListを初期化する。
    this.screenInfoList = new ArrayList<>();
    // 5. コントローラ.screenInfoListに要素を追加する。
    this.screenInfoList.add(DAC2015_LaborCostInfoEntryCns.GAMEN_SENI_SEARCH);
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public StreamedContent confirmExportCsv() throws AZA_ApplicationException {
    return entryLgc.exportCsvBasicInputMain(basicInputSearchDto, null);
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void inquiryPreview() throws AZA_ApplicationException {
    // 1. 条件を判定する。
    if (DAC2015_LaborCostInfoEntryCns.PROCESS_MODE_DELETE.equals(this.processModeInquiry)) {
      // 2. コントローラ.processModeInquiryに値を設定する。
      this.processModeInquiry = null;
      // 3. 条件を判定する。
      if (this.basicInputListDetailDtoList != null && !this.basicInputListDetailDtoList.isEmpty()) {
        this.basicInputSearchDto = copyBasicInputSearchDto(this.basicInputPrevSearchDto);
      }
      // 4. 処理を実行する。
      this.screenInfoList.remove(DAC2015_LaborCostInfoEntryCns.GAMEN_SENI_CONFIRM);
      return;
    }
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void inquiryReference() throws AZA_ApplicationException {
    // 1. コントローラ.buttonIdに値を設定する。
    this.buttonId = BTN_CONFIRM_REFERENCE;
    // 2. attachmentFileCtlをオープンする。
    attachmentFileCtl.open();
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void inquiryConfirm() throws AZA_ApplicationException {
    // 1. ロジック#confirmInquiryMainを実行し、戻り値をコントローラ.basicInputListDetailDtoListに設定する。
    this.basicInputListDetailDtoList = entryLgc.confirmInquiryMain(this.basicInputSearchDto, firstSelectedBasicInput());
    // 2. コントローラ.processModeInquiryに値を設定する。
    this.processModeInquiry = null;
    // 3. 処理を実行する。
    this.screenInfoList.remove(DAC2015_LaborCostInfoEntryCns.GAMEN_SENI_CONFIRM);
  }

  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void selected() throws AZA_ApplicationException {
    // 1. 条件を判定する。
    if (BTN_BASIC_KYUYO_KASHO_SEARCH.equals(this.buttonId)) {
      return;
    }
    // 2. 条件を判定する。
    if (BTN_BASIC_INDUSTRY_SEARCH.equals(this.buttonId)) {
      return;
    }
    // 3. 条件を判定する。
    if (BTN_BASIC_DEPT_SEARCH.equals(this.buttonId)) {
      return;
    }
  }

  private DAC2015_LaborCostInfoEntryListDetailDto firstSelectedInput() {
    if (this.listDetailSel == null || this.listDetailSel.isEmpty()) {
      return new DAC2015_LaborCostInfoEntryListDetailDto();
    }
    return this.listDetailSel.get(0);
  }

  private DAC2015_LaborCostInfoEntryBasicInputListDetailDto firstSelectedBasicInput() {
    if (this.basicInputListDetailSel == null || this.basicInputListDetailSel.isEmpty()) {
      return new DAC2015_LaborCostInfoEntryBasicInputListDetailDto();
    }
    return this.basicInputListDetailSel.get(0);
  }

  private String getMessageByIndex(List<String> messageList, int index) {
    if (messageList == null || messageList.size() <= index) {
      return null;
    }
    return messageList.get(index);
  }

  private DAA_ShinseiJohoDto firstOrDefaultShinsei(DAA_ShinseiShoninJohoDto shinseiShoninJohoDto) {
    if (shinseiShoninJohoDto == null || shinseiShoninJohoDto.getShinseiJohoDtoList() == null
        || shinseiShoninJohoDto.getShinseiJohoDtoList().isEmpty()) {
      return new DAA_ShinseiJohoDto();
    }
    return shinseiShoninJohoDto.getShinseiJohoDtoList().get(0);
  }

  private DAA_RirekiJohoDto firstOrDefaultRireki(DAA_ShinseiShoninJohoDto shinseiShoninJohoDto) {
    if (shinseiShoninJohoDto == null || shinseiShoninJohoDto.getRirekiJohoDtoList() == null
        || shinseiShoninJohoDto.getRirekiJohoDtoList().isEmpty()) {
      return new DAA_RirekiJohoDto();
    }
    return shinseiShoninJohoDto.getRirekiJohoDtoList().get(0);
  }

  private void applyDetailPanelMatrix(DAC2015_LaborCostInfoEntryBasicInputListDetailDto dto) {
    String nyuryokuKbnCd = dto == null ? null : dto.getNyuryokuKbnCd();
    this.isKyuyoShikyuPanelRendered = "1".equals(nyuryokuKbnCd);
    this.isKyuyoKojoPanelRendered = "2".equals(nyuryokuKbnCd);
    this.isKaifutanKempoPanelRendered = "3".equals(nyuryokuKbnCd);
    this.isKaifutanNenkinPanelRendered = "4".equals(nyuryokuKbnCd);
    this.isYochokinPanelRendered = "5".equals(nyuryokuKbnCd);
    this.isShiharaisakiRendered = this.isKaifutanKempoPanelRendered || this.isKaifutanNenkinPanelRendered;
  }

  private DAC2015_LaborCostInfoEntrySearchDto copySearchDto(
      DAC2015_LaborCostInfoEntrySearchDto source) {
    DAC2015_LaborCostInfoEntrySearchDto target = new DAC2015_LaborCostInfoEntrySearchDto();
    if (source == null) {
      return target;
    }
    target.setTaishoNengetsuFrom(source.getTaishoNengetsuFrom());
    target.setTaishoNengetsuTo(source.getTaishoNengetsuTo());
    target.setShoninJokyoMishusei(source.isShoninJokyoMishusei());
    target.setShoninJokyoSashimodoshi(source.isShoninJokyoSashimodoshi());
    target.setShoninJokyoShitagakiHozon(source.isShoninJokyoShitagakiHozon());
    target.setShoninJokyoShoninIraichu(source.isShoninJokyoShoninIraichu());
    target.setShoninJokyoShoninzumi(source.isShoninJokyoShoninzumi());
    return target;
  }

  private DAC2015_LaborCostInfoEntryBasicInputSearchDto copyBasicInputSearchDto(
      DAC2015_LaborCostInfoEntryBasicInputSearchDto source) {
    DAC2015_LaborCostInfoEntryBasicInputSearchDto target =
        new DAC2015_LaborCostInfoEntryBasicInputSearchDto();
    if (source == null) {
      return target;
    }
    target.setShikyubi(source.getShikyubi());
    target.setNyuryokuKbnCd(source.getNyuryokuKbnCd());
    target.setNyuryokuKbnMeisho(source.getNyuryokuKbnMeisho());
    target.setKyuyoKbnCd(source.getKyuyoKbnCd());
    target.setKyuyoKbnMeisho(source.getKyuyoKbnMeisho());
    target.setKyuyoKashoCd(source.getKyuyoKashoCd());
    target.setKyuyoKashoMeisho(source.getKyuyoKashoMeisho());
    target.setKamokuBunruiCd(source.getKamokuBunruiCd());
    target.setKamokuBunruiMeisho(source.getKamokuBunruiMeisho());
    target.setKamokuKbnCd(source.getKamokuKbnCd());
    target.setKamokuKbnMeisho(source.getKamokuKbnMeisho());
    target.setGyoshuCd(source.getGyoshuCd());
    target.setGyoshuMeisho(source.getGyoshuMeisho());
    target.setShozokuCd(source.getShozokuCd());
    target.setShozokuMeisho(source.getShozokuMeisho());
    target.setListDetailNyuryokuKbnCd(source.getListDetailNyuryokuKbnCd());
    return target;
  }
}
