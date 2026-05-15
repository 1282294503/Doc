/*
 * ClassName   DAC2015_LaborCostInfoEntryCns
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報入力 定数
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2015;

/**
 * <pre>
 * 人件費情報入力 定数
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
public class DAC2015_LaborCostInfoEntryCns {

  /** 機能識別コード */
  public static final String KINO_SHIKIBETSU_CD = "DAC2015";

  /** 処理モード.登録 */
  public static final Integer PROCESS_MODE_REGIST = 1;

  /** 処理モード.修正 */
  public static final Integer PROCESS_MODE_MODIFY = 2;

  /** 処理モード.削除 */
  public static final Integer PROCESS_MODE_DELETE = 3;

  /** 処理モード.照会 */
  public static final Integer PROCESS_MODE_INQUIRY = 4;

  /** 処理モード.確認 */
  public static final Integer PROCESS_MODE_CONFIRM = 5;

  /** ボタン種別.登録 */
  public static final Integer BUTTON_TYPE_REGIST = 1;

  /** ボタン種別.修正 */
  public static final Integer BUTTON_TYPE_MODIFY = 2;

  /** ボタン種別.削除 */
  public static final Integer BUTTON_TYPE_DELETE = 3;

  /** ボタン種別.照会 */
  public static final Integer BUTTON_TYPE_INQUIRY = 4;

  /** ボタン種別.承認依頼取下 */
  public static final Integer BUTTON_TYPE_WITHDRAW = 5;

  /** ボタン種別.次画面 */
  public static final Integer BUTTON_TYPE_NEXT = 6;

  /** ボタン種別.表示 */
  public static final Integer BUTTON_TYPE_DISPLAY = 7;

  /** ボタン種別.個別照会 */
  public static final Integer BUTTON_TYPE_INDIVIDUAL_INQUIRY = 8;

  /** 入力区分.給与支給・給与控除 */
  public static final String NYURYOKU_KBN_KYUYO_SHIKYU_KYUYO_KOJO = "0";

  /** 入力区分.給与支給・給与控除名称 */
  public static final String NYURYOKU_KBN_KYUYO_SHIKYU_KYUYO_KOJO_MEISHO = "1.給与支給<br>2.給与控除";

  // --- 人件費情報入力項目コード: 給与支給 ---

  /** 人件費情報入力項目.給与支給.支給額.役員報酬 */
  public static final String KYUYO_SHIKYU_YAKUIN_HOUSHU = "1010";

  /** 人件費情報入力項目.給与支給.支給額.役員通勤手当 */
  public static final String KYUYO_SHIKYU_YAKUIN_TSUKIN_TEATE = "1020";

  /** 人件費情報入力項目.給与支給.支給額.職員本俸 */
  public static final String KYUYO_SHIKYU_SHOKUIN_HONPO = "1030";

  /** 人件費情報入力項目.給与支給.支給額.諸手当 */
  public static final String KYUYO_SHIKYU_SHOTEATE = "1050";

  /** 人件費情報入力項目.給与支給.支給額.通勤手当 */
  public static final String KYUYO_SHIKYU_TSUKIN_TEATE = "1060";

  /** 人件費情報入力項目.給与支給.支給額.臨時者賃金 */
  public static final String KYUYO_SHIKYU_RINJISHA_CHINGIN = "1070";

  /** 人件費情報入力項目.給与支給.支給額.臨時者通勤手当 */
  public static final String KYUYO_SHIKYU_RINJISHA_TSUKIN_TEATE = "1080";

  /** 人件費情報入力項目.給与支給.支給額.教育訓練費 */
  public static final String KYUYO_SHIKYU_KYOIKU_KUNRENHI = "1090";

  /** 人件費情報入力項目.給与支給.支給額.健保給付金 */
  public static final String KYUYO_SHIKYU_KEMPO_KYUFUKIN = "1110";

  /** 人件費情報入力項目.給与支給.支給額.きずな配当金 */
  public static final String KYUYO_SHIKYU_KIZUNA_HAITOKIN = "1120";

  // --- 人件費情報入力項目コード: 給与控除 ---

  /** 人件費情報入力項目.給与控除.控除額.本会住宅貸付金 */
  public static final String KYUYO_KOJO_HONKAI_JUTAKU_KASHITSUKEKIN = "2010";

  /** 人件費情報入力項目.給与控除.控除額.普通貸付金 */
  public static final String KYUYO_KOJO_FUTSU_KASHITSUKEKIN = "2020";

  /** 人件費情報入力項目.給与控除.控除額.特別貸付金 */
  public static final String KYUYO_KOJO_TOKUBETSU_KASHITSUKEKIN = "2030";

  /** 人件費情報入力項目.給与控除.控除額.諸控除額 */
  public static final String KYUYO_KOJO_SHOKOJO_GAKU = "2040";

  /** 人件費情報入力項目.給与控除.控除額.其他控除額 */
  public static final String KYUYO_KOJO_SONOTA_KOJO_GAKU = "2050";

  /** 人件費情報入力項目.給与控除.控除額.現物給与額 */
  public static final String KYUYO_KOJO_GENBUTSU_KYUYO_GAKU = "2060";

  /** 人件費情報入力項目.給与控除.控除額.物品代 */
  public static final String KYUYO_KOJO_BUPPINDAI = "2070";

  /** 人件費情報入力項目.給与控除.控除額.食費 */
  public static final String KYUYO_KOJO_SHOKUHI = "2080";

  /** 人件費情報入力項目.給与控除.控除額.ホクレン労組費 */
  public static final String KYUYO_KOJO_HOKUREN_ROSOHI = "2090";

  /** 人件費情報入力項目.給与控除.控除額.生命保険料 */
  public static final String KYUYO_KOJO_SEIMEI_HOKENRYO = "2100";

  /** 人件費情報入力項目.給与控除.控除額.損害保険料 */
  public static final String KYUYO_KOJO_SONGAI_HOKENRYO = "2110";

  /** 人件費情報入力項目.給与控除.控除額.きずな月掛金 */
  public static final String KYUYO_KOJO_KIZUNA_TSUKIKAKEKIN = "2120";

  /** 人件費情報入力項目.給与控除.控除額.きずな臨掛金 */
  public static final String KYUYO_KOJO_KIZUNA_RINKAKEKIN = "2130";

  /** 人件費情報入力項目.給与控除.控除額.健康保険料 */
  public static final String KYUYO_KOJO_KENKO_HOKENRYO = "2140";

  /** 人件費情報入力項目.給与控除.控除額.介護保険料 */
  public static final String KYUYO_KOJO_KAIGO_HOKENRYO = "2150";

  /** 人件費情報入力項目.給与控除.控除額.年金保険料 */
  public static final String KYUYO_KOJO_NENKIN_HOKENRYO = "2160";

  /** 人件費情報入力項目.給与控除.控除額.雇用保険料 */
  public static final String KYUYO_KOJO_KOYO_HOKENRYO = "2180";

  /** 人件費情報入力項目.給与控除.控除額.所得税 */
  public static final String KYUYO_KOJO_SHOTOKUZEI = "2190";

  /** 人件費情報入力項目.給与控除.控除額.住民税 */
  public static final String KYUYO_KOJO_JUMINZEI = "2200";

  /** 人件費情報入力項目.給与控除.控除額.控除額積立貯金 */
  public static final String KYUYO_KOJO_TSUMITATE_CHOKIN = "2210";

  /** 人件費情報入力項目.給与控除.控除額.雑受取利息 */
  public static final String KYUYO_KOJO_ZATSUUKETORI_RISOKU = "2220";

  /** 人件費情報入力項目.給与控除.控除額.住宅料 */
  public static final String KYUYO_KOJO_JUTAKURYO = "2230";

  /** 人件費情報入力項目.給与控除.控除額.その他預り金 */
  public static final String KYUYO_KOJO_SONOTA_AZUKARIKIN = "2240";

  /** 人件費情報入力項目.給与控除.控除額.貯金担保徴収額 */
  public static final String KYUYO_KOJO_CHOKIN_TANPO_CHOSHU_GAKU = "2250";

  /** 人件費情報入力項目.給与控除.控除額.外部財産形成掛金 */
  public static final String KYUYO_KOJO_GAIBU_ZAISAN_KEISEI_KAKEKIN = "2260";

  /** 人件費情報入力項目.給与控除.差引振込支給額.振込額 */
  public static final String KYUYO_KOJO_FURIKOMI_GAKU = "3010";

  /** 人件費情報入力項目.給与控除.差引振込支給額.現金支給額 */
  public static final String KYUYO_KOJO_GENKIN_SHIKYU_GAKU = "3030";

  /** 人件費情報入力項目.給与控除.差引振込支給額.仮受金 */
  public static final String KYUYO_KOJO_KARIUKEKIN = "3040";

  /** 人件費情報入力項目.給与控除.差引振込支給額.赤支給差額 */
  public static final String KYUYO_KOJO_AKASHIKYU_SAGAKU = "3050";

  // --- 人件費情報入力項目コード: 会負担健保 ---

  /** 人件費情報入力項目.会負担健保.役員.健康保険料 */
  public static final String KAIFUTAN_KEMPO_YAKUIN_KENKO_HOKENRYO = "4010";

  /** 人件費情報入力項目.会負担健保.役員.介護保険料 */
  public static final String KAIFUTAN_KEMPO_YAKUIN_KAIGO_HOKENRYO = "4020";

  /** 人件費情報入力項目.会負担健保.職員.健康保険料 */
  public static final String KAIFUTAN_KEMPO_SHOKUIN_KENKO_HOKENRYO = "4030";

  /** 人件費情報入力項目.会負担健保.職員.介護保険料 */
  public static final String KAIFUTAN_KEMPO_SHOKUIN_KAIGO_HOKENRYO = "4040";

  /** 人件費情報入力項目.会負担健保.臨時.健康保険料 */
  public static final String KAIFUTAN_KEMPO_RINJI_KENKO_HOKENRYO = "4050";

  /** 人件費情報入力項目.会負担健保.臨時.介護保険料 */
  public static final String KAIFUTAN_KEMPO_RINJI_KAIGO_HOKENRYO = "4060";

  // --- 人件費情報入力項目コード: 会負担年金 ---

  /** 人件費情報入力項目.会負担年金.役員.厚生年金保険料 */
  public static final String KAIFUTAN_NENKIN_YAKUIN_KOSEI_NENKIN_HOKENRYO = "5010";

  /** 人件費情報入力項目.会負担年金.役員.子供子育て拠出金 */
  public static final String KAIFUTAN_NENKIN_YAKUIN_KODOMO_KOSODATE_KYOSHUTSUKIN = "5020";

  /** 人件費情報入力項目.会負担年金.職員.厚生年金保険料 */
  public static final String KAIFUTAN_NENKIN_SHOKUIN_KOSEI_NENKIN_HOKENRYO = "5030";

  /** 人件費情報入力項目.会負担年金.職員.子供子育て拠出金 */
  public static final String KAIFUTAN_NENKIN_SHOKUIN_KODOMO_KOSODATE_KYOSHUTSUKIN = "5040";

  /** 人件費情報入力項目.会負担年金.臨時.厚生年金保険料 */
  public static final String KAIFUTAN_NENKIN_RINJI_KOSEI_NENKIN_HOKENRYO = "5050";

  /** 人件費情報入力項目.会負担年金.臨時.子供子育て拠出金 */
  public static final String KAIFUTAN_NENKIN_RINJI_KODOMO_KOSODATE_KYOSHUTSUKIN = "5060";

  // --- 人件費情報入力項目コード: 預貯金 ---

  /** 人件費情報入力項目.預貯金.支給額.定期貯金 */
  public static final String YOCHOKIN_SHIKYU_TEIKI_CHOKIN = "6010";

  /** 人件費情報入力項目.預貯金.支給額.積立貯金 */
  public static final String YOCHOKIN_SHIKYU_TSUMITATE_CHOKIN = "6020";

  /** 人件費情報入力項目.預貯金.支給額.雑支払利息 */
  public static final String YOCHOKIN_SHIKYU_ZATSUSHIHARAI_RISOKU = "6030";

  /** 人件費情報入力項目.預貯金.支給額.給与徴収金 */
  public static final String YOCHOKIN_SHIKYU_KYUYO_CHOSHUKIN = "6040";

  /** 人件費情報入力項目.預貯金.控除額.利息税国税 */
  public static final String YOCHOKIN_KOJO_RISOKUZEI_KOKUZEI = "7010";

  /** 人件費情報入力項目.預貯金.控除額.利息税地方税 */
  public static final String YOCHOKIN_KOJO_RISOKUZEI_CHIHOZEI = "7020";

  /** 人件費情報入力項目.預貯金.控除額.定期貯金 */
  public static final String YOCHOKIN_KOJO_TEIKI_CHOKIN = "7030";

  /** 人件費情報入力項目.預貯金.控除額.貯金担保貸付金 */
  public static final String YOCHOKIN_KOJO_CHOKIN_TANPO_KASHITSUKEKIN = "7040";

  /** 人件費情報入力項目.預貯金.控除額.貯金貸付利息 */
  public static final String YOCHOKIN_KOJO_CHOKIN_KASHITSUKE_RISOKU = "7050";

  /** 人件費情報入力項目.預貯金.差引振込支給額.振込額 */
  public static final String YOCHOKIN_SASHIHIKI_FURIKOMI_GAKU = "8010";

  // --- 初期値 ---

  /** 初期値.人件費科目分類補助コード */
  public static final String SHOKICHI_JINKENHI_KAMOKU_BUNRUI_HOJO_CD = "  ";

  /** 初期値.人件費項目区分 */
  public static final String SHOKICHI_JINKENHI_KOMOKU_KBN = " ";

  /** 初期値.会負担保険料区分 */
  public static final String SHOKICHI_KAIFUTAN_HOKENRYO_KBN = " ";

  // --- 添付ファイル処理区分 ---

  /** 添付ファイル処理区分.処理なし */
  public static final String TENPU_FILE_SHORI_KBN_NASHI = "1";

  /** 添付ファイル処理区分.アップロード */
  public static final String TENPU_FILE_SHORI_KBN_UPLOAD = "2";

  /** 添付ファイル処理区分.削除 */
  public static final String TENPU_FILE_SHORI_KBN_DELETE = "3";

  /** 添付ファイル処理区分.削除・アップロード */
  public static final String TENPU_FILE_SHORI_KBN_DELETE_UPLOAD = "4";

  // --- 画面遷移情報 ---

  /** 画面遷移情報.照会 */
  public static final String GAMEN_SENI_SEARCH = "照会";

  /** 画面遷移情報.入力 */
  public static final String GAMEN_SENI_INPUT = "入力";

  /** 画面遷移情報.確認 */
  public static final String GAMEN_SENI_CONFIRM = "確認";
}
