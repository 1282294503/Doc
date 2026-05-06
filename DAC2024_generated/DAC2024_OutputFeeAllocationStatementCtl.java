/*
 * ClassName   DAC2024_OutputFeeAllocationStatementCtl
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     手数料配分計算書出力 コントローラ
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2024;

import java.time.LocalDate;
import java.util.List;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.interceptor.Interceptors;
import jp.or.hokuren.aza.exception.AZA_ApplicationException;
import jp.or.hokuren.aza.log.annotation.AZA_ControllerLogInterceptor;
import jp.or.hokuren.aza.log.annotation.AZA_StartupLoggingInterceptor;
import jp.or.hokuren.aza.log.annotation.AZA_StartupMsgKanrNum;
import jp.or.hokuren.aza.session.AZA_LogonInfoDto;
import jp.or.hokuren.daa.def.DAC_MessageNoCns;
import jp.or.hokuren.daa.view.daa1108.DAA1108_IndustrySearchCtl;
import jp.or.hokuren.daa.view.daa1108.DAA1108_IndustrySearchRequestDto;
import jp.scsk.arvicio3.core.context.extension.NextPageScoped;
import jp.scsk.arvicio3.web.annotation.HandleBusinessValidation;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.StreamedContent;

/**
 * <pre>
 * 手数料配分計算書出力 コントローラ
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Named("dac2024ctl")
@NextPageScoped
@HandleBusinessValidation
public class DAC2024_OutputFeeAllocationStatementCtl {

  // インジェクションクラス
  /** ログオン情報 */
  @Inject
  private AZA_LogonInfoDto logonInfo;

  /** 手数料配分計算書出力ロジック */
  @Inject
  private DAC2024_OutputFeeAllocationStatementLgc logic;

  /** 業種検索コントローラ */
  @Inject
  private DAA1108_IndustrySearchCtl industrySearchCtl;

  // フィールド
  /** 初期化済フラグ */
  @Getter
  @Setter
  private Boolean initialized = false;

  /** 操作メッセージ */
  @Getter
  @Setter
  private String exportOperationMessage = null;

  /** 運用管理日 */
  @Getter
  @Setter
  private LocalDate unyoKanribi = null;

  /** ボタンID */
  @Getter
  @Setter
  private String buttonId = null;

  /** 業種ラベル表示フラグ */
  @Getter
  @Setter
  private Boolean industryLabelDisplayFlag = false;

  /** 検索条件Dto */
  @Getter
  @Setter
  private DAC2024_OutputFeeAllocationStatementSearchDto searchConditionDto =
      new DAC2024_OutputFeeAllocationStatementSearchDto();

  /** 初期検索条件Dto */
  @Getter
  @Setter
  private DAC2024_OutputFeeAllocationStatementSearchDto initSearchConditionDto =
      new DAC2024_OutputFeeAllocationStatementSearchDto();

  /**
   * <pre>
   * 手数料配分計算書出力 初期表示
   * 初期表示時に実行する処理を実施する。
   * </pre>
   *
   * @throws AZA_ApplicationException 業務例外
   */
  @AZA_StartupMsgKanrNum(DAC_MessageNoCns.DAC80000I)
  @Interceptors({AZA_StartupLoggingInterceptor.class, AZA_ControllerLogInterceptor.class})
  public void init() throws AZA_ApplicationException {

    // 初期化済の場合は処理をスキップする。
    if (Boolean.TRUE.equals(initialized)) {
      return;
    }

    // ロジック#初期処理メインを実行する。
    DAC2024_InitMainResponseRecord result = logic.initMain(searchConditionDto);

    // コントローラのフィールドにロジックの戻り値を設定する。
    this.initSearchConditionDto = result.searchConditionDto();
    this.searchConditionDto = result.searchConditionDto();
    this.unyoKanribi = result.unyoKanribi();
    this.exportOperationMessage = result.exportOperationMessage();
    this.industryLabelDisplayFlag = result.industryLabelDisplayFlag();

    // 初期化済フラグをtrueに設定する。
    this.initialized = true;
  }

  /**
   * <pre>
   * 手数料配分計算書出力 クリア処理
   * 検索条件を初期状態に戻す。
   * </pre>
   *
   * @throws AZA_ApplicationException 業務例外
   */
  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void clear() throws AZA_ApplicationException {

    // 初期検索条件Dtoを検索条件Dtoにコピーする。
    this.searchConditionDto = initSearchConditionDto;
  }

  /**
   * <pre>
   * 手数料配分計算書出力 表示処理
   * 業種名称を更新する。
   * </pre>
   *
   * @throws AZA_ApplicationException 業務例外
   */
  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void show() throws AZA_ApplicationException {

    // ロジック#表示処理メインを実行する。
    DAC2024_ShowMainResponseRecord result = logic.showMain(searchConditionDto, unyoKanribi);

    // 業種名称を検索条件Dtoに設定する。
    this.searchConditionDto.setGyoshuMei(result.gyoshuMei());
  }

  /**
   * <pre>
   * 手数料配分計算書出力 CSV出力
   * CSV ファイルを出力する。
   * </pre>
   *
   * @return StreamedContent CSVファイル
   * @throws AZA_ApplicationException 業務例外
   */
  @Interceptors(AZA_ControllerLogInterceptor.class)
  public StreamedContent exportCsv() throws AZA_ApplicationException {

    // ロジック#CSV出力メインを実行する。
    return logic.exportCsvMain(searchConditionDto, unyoKanribi);
  }

  /**
   * <pre>
   * 手数料配分計算書出力 業種検索ダイアログ表示
   * 業種検索ダイアログを表示する。
   * </pre>
   *
   * @param buttonId ボタンID
   * @throws AZA_ApplicationException 業務例外
   */
  @Interceptors(AZA_ControllerLogInterceptor.class)
  public void showIndustrySearch(String buttonId) throws AZA_ApplicationException {

    // ボタンIDを設定する。
    this.buttonId = buttonId;

    // 参照権限区分に応じて filterGyoshuCd を設定する。
    DAA1108_IndustrySearchRequestDto requestDto = new DAA1108_IndustrySearchRequestDto();

    if (DAC2024_LocalCns.SANSHO_KENGEN_NOT_KEIRIBU.equals(searchConditionDto.getSanshoKengen())) {
      // 経理部以外の場合は、選択可能業種コードリストを設定する。
      List<String> selectableGyoshuCdList = searchConditionDto.getSelectableGyoshuCdList();
      requestDto.setFilterGyoshuCd(selectableGyoshuCdList);
    }

    // 業種検索コントローラ#ダイアログ表示を実行する。
    industrySearchCtl.showDialogOpen(requestDto);
  }
}
