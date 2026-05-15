package jp.or.hokuren.daa.def;

import java.time.LocalDate;

/*
 * ClassName DAA_CommonsCns
 *
 * Author SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process 共通定数定義
 *
 * Notes
 *
 * History
 *
 */
public class DAA_CommonsCns {

  /** 運用管理コード.統合会計共通 */
  public static final String UNYO_KANRI_CD_DAA = "DAA";

  /** 運用管理コード.会計統合システム */
  public static final String UNYO_KANRI_CD_DAB = "DAB";

  /** 運用管理コード.発生源情報Webシステム */
  public static final String UNYO_KANRI_CD_DAC = "DAC";

  /** 一覧画面表示上限件数 */
  public static final int ICHIRAN_HYOJI_MAX_CNT = 5000;

  /** CSV出力上限件数 */
  public static final int CSV_OUTPUT_MAX_CNT = 100000;

  /** 出力順(会員別・系統利用高関連調書).未収金 */
  public static final String SHUTSURYOKU_JUN_MISHUKIN = "1";

  /** 出力順(会員別・系統利用高関連調書).未払金 */
  public static final String SHUTSURYOKU_JUN_MIHARAIKIN = "2";

  /** 出力順(会員別・系統利用高関連調書).共計支払金 */
  public static final String SHUTSURYOKU_JUN_KYOKEI_SHIHARAIKIN = "3";

  /** 夜間バッチ実行指示.初期 */
  public static final String YAKAN_BATCH_JIKKO_SHIJI_SYOKI = "0";

  /** 夜間バッチ実行指示.実行指示 */
  public static final String YAKAN_BATCH_JIKKO_SHIJI_JIKKO_SHIJI_ = "1";

  /** 夜間バッチ実行指示.実行済 */
  public static final String YAKAN_BATCH_JIKKO_SHIJI_JIKKO_ZUMI = "2";

  /** 主計課コード */
  public static final String SHUKEIKA_CD = "4118";

  /** バッチ処理結果情報登録.成功 */
  public static final String BATCH_SHORI_KEKKA_SUCCESS = "0";

  /** バッチ処理結果情報登録.エラー */
  public static final String BATCH_SHORI_KEKKA_ERROR = "1";

  /** 経理課コード */
  public static final String DAA_SHOZOKU_KEIRI = "4120";

  /** 財務課コード */
  public static final String DAA_SHOZOKU_ZAIMU = "4119";

  /** 決算モード.決算仕訳以外 */
  public static final String KESSAN_MODE_OHTER = "0";

  /** 決算モード.決算仕訳 */
  public static final String KESSAN_MODE_KESSAN = "1";

  /** 申請承認アクション.申請 */
  public static final String ACTION_SHINSEI = "01";

  /** 申請承認アクション.再申請 */
  public static final String ACTION_SAI_SHINSEI = "02";

  /** 申請承認アクション.承認 */
  public static final String ACTION_SHONIN = "03";

  /** 申請承認アクション.差戻 */
  public static final String ACTION_SASHIMODOSHI = "04";

  /** 申請承認アクション.取下 */
  public static final String ACTION_TORISAGE = "05";

  /** 申請承認アクション.申請取り消し */
  public static final String ACTION_TORIKESHI = "06";

  /** 会計承認状態.承認依頼中 */
  public static final String SHONIN_IRAI_CHU = "01";

  /** 会計承認状態.差戻 */
  public static final String SHONIN_SASHIMODOSHI = "02";

  /** 会計承認状態.承認済 */
  public static final String SHONIN_ZUMI = "03";

  /** 項科目コード.内部未収金 */
  public static final String KOKAMOKU_CD_NAIBUMISHUKIN = "110918";

  /** 項科目コード.内部未払金 */
  public static final String KOKAMOKU_CD_NAIBUMIHARAIKIN = "210718";

  /** 利率適用対象区分.滞留利率 */
  public static final String RIRITSU_TAISHO_KBN_TAIRYU = "0";

  /** 利率適用対象区分.立替利率 */
  public static final String RIRITSU_TAISHO_KBN_TATEKAE = "1";

  /** 支払案内書作成状況.未作成 */
  public static final String SHIHARAI_ANNAISHO_SAKUSEI_JOKYO_MISAKUSEI = "0";

  /** 支払案内書作成状況.作成中 */
  public static final String SHIHARAI_ANNAISHO_SAKUSEI_JOKYO_SAKSEICHU = "1";

  /** 支払案内書作成状況.作成済 */
  public static final String SHIHARAI_ANNAISHO_SAKUSEI_JOKYO_SAKSEIZUMI = "2";

  /** 請求書作成区分.汎用請求書 */
  public static final String SEIKYUSHO_SAKUSEI_KBN_HANYO = "0";

  /** 請求書作成区分.共通請求書 */
  public static final String SEIKYUSHO_SAKUSEI_KBN_KYOTSU = "1";
  
  /** 請求書利用区分.利用あり */
  public static final String SEIKYUSHO_RIYO_KBN_ARI = "0";
  
  /** 請求書利用区分.利用なし */
  public static final String SEIKYUSHO_RIYO_KBN_NASHI = "1";

  /** 請求書様式.Ａ様式 */
  public static final String SEIKYUSHO_YOSHIKI_A = "A";

  /** 請求書様式.Ｂ様式 */
  public static final String SEIKYUSHO_YOSHIKI_B = "B";

  /** 請求書ファイル名.鑑 */
  public static final String SEIKYUSYO_FILE_NAME_KAGAMI = "1";

  /** 請求書ファイル名.明細（ＰＤＦ） */
  public static final String SEIKYUSYO_FILE_NAME_PDF = "2";

  /** 請求書ファイル名.明細（ＣＳＶ） */
  public static final String SEIKYUSYO_FILE_NAME_CSV = "3";

  /** 相手先コード桁数モード.5桁モード */
  public static final String AITESAKI_CD_FIVE_DIGIT_MODE = "1";

  /** 相手先コード桁数モード.8桁モード */
  public static final String AITESAKI_CD_EIGHT_DIGIT_MODE = "2";

  /** 相手先コード桁数モード.5桁8桁混合モード */
  public static final String AITESAKI_CD_MIX_DIGIT_MODE = "3";

  /** 請求書ファイル名.Ａ様式 */
  public static final String SEIKYUSYO_FILE_NAME_A = "01";

  /** 請求書ファイル名.Ｂ様式 */
  public static final String SEIKYUSYO_FILE_NAME_B = "02";

  /** 請求書ファイル名.汎用様式 */
  public static final String SEIKYUSYO_FILE_NAME_HANYO = "99";

  /** 項科目コード.購買共計支払金 */
  public static final String KOKAMOKU_CD_KOBAI_KYOKEI_SHIHARAIKIN = "110601";

  /** 項科目コード.販売共計支払金 */
  public static final String KOKAMOKU_CD_HANBAI_KYOKEI_SHIHARAIKIN = "110602";

  /** 項科目コード.購買共計受入金 */
  public static final String KOKAMOKU_CD_KOBAI_KYOKEI_UKEIREKIN = "210501";

  /** 項科目コード.販売共計受入金 */
  public static final String KOKAMOKU_CD_HANBAI_KYOKEI_UKEIREKIN = "210502";

  /** 項科目コード.電子記録債権 */
  public static final String KOKAMOKU_CD_DENSHI_KIROKU_SAIKEN = "110402";

  /** 科目コード.購買共計受入金 消費税 */
  public static final String KAMOKU_CD_KOBAI_KYOKEI_UKEIREKIN_SYOHIZEI = "21050129";

  /** 科目コード.販売共計受入金 消費税 */
  public static final String KAMOKU_CD_HANBAI_KYOKEI_UKEIREKIN_SYOHIZEI = "21050229";

  /** 科目コード.預り金 消費税 */
  public static final String KAMOKU_CD_AZUKARIKIN_SYOHIZEI = "21070303";

  /** 所属別メニューグループ全組織 */
  public static final String SHOZOKUBETSUMENUGROUP_ZENSOSHIKI = "DAAZENSOSHIKI_ATODENAOSU";

  /** 所属別メニューグループ主計課 */
  public static final String SHOZOKUBETSUMENUGROUP_SYUKEIKA = "DAASYUKEIKA_ATODENAOSU";

  /** 所属別メニューグループ経理課 */
  public static final String SHOZOKUBETSUMENUGROUP_KEIRIKA = "DAAKEIRIKA_ATODENAOSU";

  /** 所属別メニューグループ財務課 */
  public static final String SHOZOKUBETSUMENUGROUP_ZAIMUKA = "DAAZAIMUKA_ATODENAOSU";

  /** 所属別メニューグループ審査課 */
  public static final String SHOZOKUBETSUMENUGROUP_SHINSAKA = "DAASHINSAKA_ATODENAOSU";

  /** 所属別メニューグループ本所（現業部門） */
  public static final String SHOZOKUBETSUMENUGROUP_HONSHOGENGYO = "DAAHONSHOGENGYO_ATODENAOSU";

  /** 所属別メニューグループ支所（現業部門） */
  public static final String SHOZOKUBETSUMENUGROUP_SHISHOGENGYO = "DAASHISHOGENGYO_ATODENAOSU";

  /** 所属別メニューグループ人事課 */
  public static final String SHOZOKUBETSUMENUGROUP_JINJIKA = "DAAJINJIKA_ATODENAOSU";

  /** 所属別メニューグループICT・HIS */
  public static final String SHOZOKUBETSUMENUGROUP_ICTHIS = "DAAICTHIS_ATODENAOSU";

  /** 所属別メニューグループ事業所管理担当課 */
  public static final String SHOZOKUBETSUMENUGROUP_JIGYOSHOKANRIKA =
      "DAAJIGYOSHOKANRIKA_ATODENAOSU";

  /** 所属別メニューグループ施設（給与個所） */
  public static final String SHOZOKUBETSUMENUGROUP_SHISETSUKYUYO = "DAASHISETSUKYUYO_ATODENAOSU";

  /** 所属別メニューグループ施設（給与個所以外） */
  public static final String SHOZOKUBETSUMENUGROUP_SHISETSUNOTKYUYO =
      "DAASHISETSUNOTKYUYO_ATODENAOSU";

  /** 所属別メニューグループ管理本部各課 */
  public static final String SHOZOKUBETSUMENUGROUP_KANRIHONBU = "DAAKANRIHONBU_ATODENAOSU";

  /** 所属別メニューグループ監査法人等 */
  public static final String SHOZOKUBETSUMENUGROUP_KANSAHOJINETC = "DAAKANSAHOJINETC_ATODENAOSU";

  /** バッチ処理結果情報登録.警告 */
  public static final String BATCH_SHORI_KEKKA_WARNING = "2";

  /** IF明細ステータス.未処理 */
  public static final String IF_DETAIL_STATUS_MISHORI = "00";

  /** IF明細ステータス.処理中 */
  public static final String IF_DETAIL_STATUS_SHORICHU = "10";

  /** IF明細ステータス.処理済 */
  public static final String IF_DETAIL_STATUS_SHORIZUMI = "20";

  /** IF明細ステータス.業務エラー */
  public static final String IF_DETAIL_STATUS_GYOMUERROR = "30";

  /** IF明細ステータス.システムエラー */
  public static final String IF_DETAIL_STATUS_SYSTEMERROR = "40";

  /** システム管理コード.統合会計システム */
  public static final String SYSTEM_KANRI_TOGO_KAIKEI_SYSTEM = "DA";

  /** 債権消込区分.回収 */
  public static final String SAIKEN_KESHIKOMI_KBN_KAISYU = "1";

  /** 債権消込区分.振替 */
  public static final String SAIKEN_KESHIKOMI_KBN_FURIKAE = "2";

  /** 債権消込区分.相殺（入力） */
  public static final String SAIKEN_KESHIKOMI_KBN_SOSAI_NYURYOKU = "3";

  /** 債権消込区分.相殺（承認） */
  public static final String SAIKEN_KESHIKOMI_KBN_SOSAI_SYONIN = "4";

  /** 債権消込区分.相殺（計上） */
  public static final String SAIKEN_KESHIKOMI_KBN_SOSAI_KEIJYO = "5";

  /** 債権消込区分.内部振替（入力） */
  public static final String SAIKEN_KESHIKOMI_KBN_NAIBUFURIKAE_NYURYOKU = "6";

  /** 債権消込区分.内部振替（承認） */
  public static final String SAIKEN_KESHIKOMI_KBN_NAIBUFURIKAE_SYONIN = "7";

  /** 取消区分.取消なし */
  public static final String TORIKESHI_KBN_TORIKESHI_NASHI = "1";

  /** 取消区分.取消あり */
  public static final String TORIKESHI_KBN_TORIKESHI_ARI = "2";

  /** 会計年度開始月日 */
  public static final String GAPPI_KAIKENENDO_KAISHI = "0401";

  /** 会計年度終了月日 */
  public static final String GAPPI_KAIKENENDO_SYURYO = "0331";

  /** 会計年度開始月 */
  public static final int TUKI_KAIKENENDO_KAISHI = 4;

  /** 会計年度開始日 */
  public static final int NITI_KAIKENENDO_KAISHI = 1;

  /** 会計年度終了月 */
  public static final int TUKI_KAIKENENDO_SYURYO = 3;

  /** 会計年度終了日 */
  public static final int NITI_KAIKENENDO_SYURYO = 31;

  /** 文字.全角スペース */
  public static final String MOJI_ZENKAKU_SPACE = "　";

  /** 文字.半角スペース */
  public static final String MOJI_HANKAKU_SPACE = " ";

  /** 月次残高データ種類.月次残高 */
  public static final String GETSUJI_ZANDAKA_DATA_SHURUI_GETSUJI = "1";

  /** 月次残高データ種類.期首残高 */
  public static final String GETSUJI_ZANDAKA_DATA_SHURUI_KISHU = "2";

  /** 残高出力パターン.財務会計 */
  public static final String SHUTSURYOKU_PATTERN_ZAIMU = "1";

  /** 残高出力パターン.管理会計 */
  public static final String SHUTSURYOKU_PATTERN_KANRI = "2";

  /** 文字.全角スラッシュ */
  public static final String MOJI_ZENKAKU_SLASH = "／";

  /** 文字.半角スラッシュ */
  public static final String MOJI_HANKAKU_SLASH = "/";

  /** 項科目コード.販売手数料 */
  public static final String KOKAMOKU_CD_HANBAI_TESURYO = "400103";

  /** 振込支払必要日数 */
  public static final int FURIKOMI_SHIHARAI_HITSUYO_NISSU = 3;

  /** 預金款科目 */
  public static final String KAN_KAMOKU_YOKIN = "1102";

  /** 取消伝票区分.取消済 */
  public static final String TORIKESHI_DEMPYO_KBN_ZUMI = "1";

  /** 取消伝票区分.未取消 */
  public static final String TORIKESHI_DEMPYO_KBN_MI = null;

  /** 科目摘要コード先頭２桁.全業種利用可能な科目摘要 */
  public static final String KAMOKUTEKIYO_CODE_9A = "9A";

  /** 科目摘要コード先頭２桁.全業種・全科目利用可能な科目摘要 */
  public static final String KAMOKUTEKIYO_CODE_9B = "9B";

  /** 業種.汎用請求書 */
  public static final String GYOSYU_HANYOSEIKYUSYO = "**";

  /** 一時データ区分.一時保持 */
  public static final String TMP_KBN_ICHIJI_HOJI = "1";

  /** 一時データ区分.通常 */
  public static final String TMP_KBN_TSUUJOU = "0";

  /** 相手先区分.取引先 */
  public static final String AITESAKI_KBN_TORIHIKISAKI = "1";

  /** 相手先区分.職員 */
  public static final String AITESAKI_KBN_SHOKUIN = "3";

  /** 対象ステータスラベル.月実績確定 */
  public static final String TAISHO_STATUS_LABEL_JISSEKI_KAKUTEI = "月実績確定";

  /** 対象ステータスラベル.月実績作成中 */
  public static final String TAISHO_STATUS_LABEL_JISSEKI_SAKUSEICHU = "月実績作成中";

  /** プルダウン出力しない.ラベル */
  public static final String PULL_DOWN_SHUTSURYOKU_LABEL = "出力しない";

  /** プルダウン出力しない.コード */
  public static final String PULL_DOWN_SHUTSURYOKU_CD = "1";

  /** 回収支払区分.回収 */
  public static final String KAISHU_SHIHARAI_KBN_KAISHU = "1";

  /** 回収支払区分.支払 */
  public static final String KAISHU_SHIHARAI_KBN_SHIHARAI = "2";

  /** 回収支払方法.現金 */
  public static final String KAISHU_SHIHARAI_HOHO_GENKIN = "1";

  /** 回収支払方法.振込 */
  public static final String KAISHU_SHIHARAI_HOHO_HURIKOMI = "2";

  /** 回収支払方法.自動引き落とし */
  public static final String KAISHU_SHIHARAI_HOHO_HIKIOTOSHI = "6";

  /** 回収支払方法.締め後振込 */
  public static final String KAISHU_SHIHARAI_HOHO_SHIMEGO = "A";

  /** 回収支払方法.外国送金 */
  public static final String KAISHU_SHIHARAI_HOHO_GAIKOKU = "H";

  /** 回収支払方法.納付書払 */
  public static final String KAISHU_SHIHARAI_HOHO_NOHUSHO = "N";

  /** 入出金区分.入金 */
  public static final String NYUSHUKKIN_KBN_NYUKIN = "1";

  /** 入出金区分.出金 */
  public static final String NYUSHUKKIN_KBN_SHUKKIN = "2";

  /** 項科目コード.普通預金 */
  public static final String KOKAMOKU_CD_FTSUYOKIN = "110202";

  /** 科目摘要コード.一般 */
  public static final String KAMOKU_TEKIYO_CD_IPPAN = "9036";

  /** 項科目コード.受取手形 */
  public static final String KOKAMOKU_UKETORI_TEGATA = "110401";

  /** 項科目コード.購買未収金 */
  public static final String KOKAMOKU_KOBAI_MISHUKIN = "110501";

  /** 項科目コード.販売未収金 */
  public static final String KOKAMOKU_HANBAI_MISHUKIN = "110502";

  /** 項科目コード.子会社貸付金 */
  public static final String KOKAMOKU_KOGAISHA_KASHITSUKEKIN = "110916";

  /** 項科目コード.未収金 */
  public static final String KOKAMOKU_MISHUKIN = "110901";

  /** 項科目コード.立替金 */
  public static final String KOKAMOKU_TATEKAEKIN = "110902";

  /** 項科目コード.従業員貸付金 */
  public static final String KOKAMOKU_JUGYOIN_KASHITSUKEKIN = "110908";

  /** 項科目コード.管理未収金 */
  public static final String KOKAMOKU_KANRI_MISHUKIN = "120401";

  /** 項科目コード.購買未払金 */
  public static final String KOKAMOKU_KOBAI_MIHARAIKIN = "210401";

  /** 項科目コード.販売未払金 */
  public static final String KOKAMOKU_HANBAI_MIHARAIKIN = "210402";

  /** 項科目コード.諸掛未払金 */
  public static final String KOKAMOKU_SHOGAKARI_MIHARAIKIN = "210404";

  /** 項科目コード.未払金 */
  public static final String KOKAMOKU_MIHARAIKIN = "210701";

  /** 項科目コード.雑費 */
  public static final String KOKAMOKU_CD_ZAPPI = "300208";

  /** 項科目コード.雑費用 */
  public static final String KOKAMOKU_CD_ZATSUHIYO = "300406";

  /** 項科目コード.システム入金勘定 */
  public static final String KOKAMOKU_CD_SYSTEM_NYUKIN__KANJO = "219005";

  /** 項科目コード.未収金 */
  public static final String KOKAMOKU_CD_MISHUKIN = "110901";

  /** 項科目コード.借受金 */
  public static final String KOKAMOKU_CD_MIHARAIKIN = "210707";

  /** 事業未収金款科目 */
  public static final String JIGYO_MISHUKIN_KAN_KAMOKU = "1105";

  /** 管理対象区分.債権明細データ */
  public static final String KANRITAISYO_KBN_SAIKEN_MEISAI_DATA = "01";

  /** 管理対象区分.債権消込明細データ */
  public static final String KANRITAISYO_KBN_SAIKEN_KESHIKOMI_MEISAI_DATA = "02";

  /** 事業所.本所 */
  public static final String JIGYOSHO_HONSHO = "90";

  /** ＣＭＳ連動区分.連動しない */
  public static final String CMS_RENDO_KBN_RENDOSHINAI = "0";

  /** ＣＭＳ連動区分.連動する */
  public static final String CMS_RENDO_KBN_RENDOSURU = "1";

  /** 項科目コード.システム支払勘定 */
  public static final String KOKAMOKU_CD_SYSTEM_SHIHARAI_KANJO = "119005";

  /** 目節管理区分.目節管理不要 */
  public static final String MOKUSETU_KANRI_KBN_MOKUSETU_KANRI_FUYO = "00";

  /** 業種コード.資本 */
  public static final String GYOSYU_CD_SHIHON = "99";

  /** ＣＭＳ連動状態区分.未連動 */
  public static final String CMS_RENDO_JYOTAI_KBN_MIRENDO = "0";

  /** ＣＭＳ連動状態区分.連動済 */
  public static final String CMS_RENDO_JYOTAI_KBN_RENDOZUMI = "1";

  /** 項科目コード.供給費 */
  public static final String KOKAMOKU_CD_KYOKYUHI = "300101";

  /** 代金引落通知.委託者コード */
  public static final String DAIKIN_HIKIOTOSHI_TSUCHI_ITAKUSYA_CD = "値は未定。初期セットアップまでにユーザー側で決定する。";

  /** 代金引落通知.委託者名 */
  public static final String DAIKIN_HIKIOTOSHI_TSUCHI_ITAKUSYAMEI = "値は未定。初期セットアップまでにユーザー側で決定する。";

  /** 代金引落通知.ホクレン口座コード */
  public static final String DAIKIN_HIKIOTOSHI_TSUCHI_HOKUREN_KOZA_CD =
      "値は未定。ホクレン口座マスタへの設定値確定をもって設定する。（北信連の引落先口座を設定する）";

  /** 項科目コード.販売費 */
  public static final String KOKAMOKU_CD_HANBAIHI = "300102";

  /** 全銀入払区分.入金 */
  public static final String ZENGIN_IRIHARAI_KBN_NYUKIN = "1";

  /** 全銀入払区分.出金 */
  public static final String ZENGIN_IRIHARAI_KBN_SYUKKIN = "2";

  /** レコード識別.明細 */
  public static final String RECORD_SHIKIBETSU_DETAIL = "2";

  /** レコード識別.エンド */
  public static final String RECORD_SHIKIBETSU_END = "3";

  /** データ種別.トレーラーレコード */
  public static final String DATA_SHUBETSU_TRAILER_RECORD = "ZZ";

  /** データ種別.支払先名称住所 */
  public static final String DATA_SHUBETSU_SHIHARAISAKI_MEISHO_JUSHO = "値は未定。設定値確定をもって設定する。";

  /** データ種別.支払先決済条件 */
  public static final String DATA_SHUBETSU_SHIHARAISAKI_KESSAI_JOKEN = "値は未定。設定値確定をもって設定する。";

  /** データ種別.収支管理品目情報 */
  public static final String DATA_SHUBETSU_SHUSHI_KANRI_HINMOKU = "値は未定。設定値確定をもって設定する。";

  /** データ種別.ユーザー情報 */
  public static final String DATA_SHUBETSU_USER_INF = "値は未定。設定値確定をもって設定する。";

  /** データ種別.氏名付番管理情報 */
  public static final String DATA_SHUBETSU_SHIMEI_FUBAN_KANRI = "値は未定。設定値確定をもって設定する。";

  /** データ種別.職員決済条件 */
  public static final String DATA_SHUBETSU_SHOKUIN_KESSAIJOKEN = "値は未定。設定値確定をもって設定する。";

  /** データ種別.支払先取引 */
  public static final String DATA_SHUBETSU_SHIHARAISAKI_TORIHIKISAKI = "値は未定。設定値確定をもって設定する。";

  /** データ種別.支払先農協 */
  public static final String DATA_SHUBETSU_SHIHARAISAKI_NOKYO = "値は未定。設定値確定をもって設定する。";

  /** データ種別.支払先名称住所（農協） */
  public static final String DATA_SHUBETSU_SHIHARAISAKI_MEISHO_JUSHO_NOKYO = "値は未定。設定値確定をもって設定する。";

  /** 更新区分.登録 */
  public static final String KOSHIN_KBN_REGIST = "1";

  /** 更新区分.更新 */
  public static final String KOSHIN_KBN_UPDATE = "2";

  /** 更新区分.削除 */
  public static final String KOSHIN_KBN_DELETE = "3";

  /** 採番ルール.債権OIFグループNO */
  public static final String SAIBAN_RULE_SAIKEN_OIF_GROUP_NO = "16";

  /** 採番ルール.債務OIFグループNO */
  public static final String SAIBAN_RULE_SAIMU_OIF_GROUP_NO = "17";

  /** 項科目コード.一般取扱高購買（Ａ） */
  public static final String KOKAMOKU_CD_IPPAN_TORIATSUKAIDAKA_KOBAI_A = "118001";

  /** 項科目コード.購買手数料 */
  public static final String KOKAMOKU_CD_KOBAITESURYO = "400101";

  /** 項科目コード.一般取扱高販売（Ａ） */
  public static final String KOKAMOKU_CD_IPPAN_TORIATSUKAIDAKA_HANBAI_A = "118002";

  /** 収支管理品目連動区分.未連動 */
  public static final String SHUSHI_KANRI_HINMOKU_RENDO_KBN_MIRENDO = "0";

  /** 収支管理品目連動区分.連動済 */
  public static final String SHUSHI_KANRI_HINMOKU_RENDO_KBN_RENDOZUMI = "1";

  /** 削除区分.有効 */
  public static final String SAKZYO_KBN_YUKO = "0";

  /** 削除区分.無効 */
  public static final String SAKZYO_KBN_MUKO = "1";

  /** 排他カウンタ.初期値 */
  public static final int HAITA_CNT_SHOKICHI = 0;

  /** カレンダー識別コード.ホクレン非経理日 */
  public static final String CALENDAR_SHIKIBETSU_CD_HOKUREN_HIKEIRIBI = "01";

  /** カレンダー識別コード.振込非支払日 */
  public static final String CALENDAR_SHIKIBETSU_CD_FURIKOMI_HISHIHARAIBI = "02";

  /** カレンダー識別コード.一般非支払日 */
  public static final String CALENDAR_SHIKIBETSU_CD_IPPAN_HISHIHARAIBI = "03";

  /** カレンダー識別コード.振込非回収日 */
  public static final String CALENDAR_SHIKIBETSU_CD_FURIKOMI_HIKAISHUBI = "06";

  /** カレンダー識別コード.一般非回収日 */
  public static final String CALENDAR_SHIKIBETSU_CD_IPPAN_HIKAISHUBI = "07";

  /** カレンダーコード.ホクレン非経理日 */
  public static final String CALENDAR_CD_HOKUREN_HIKEIRIBI = "      ";

  /** カレンダー前後区分.後 */
  public static final String CALENDAR_ZENGO_KBN_ATO = "1";

  /** カレンダー前後区分.前 */
  public static final String CALENDAR_ZENGO_KBN_MAE = "2";

  /** 検索区分.上位組織検索 */
  public static final String KENSAKU_KBN_JOI_SOSHIKI_KENSAKU = "2";

  /** 帳票用TSVファイル名 */
  public static final String TSV_FILE_NAME = "tsvFile";

  /** TSVファイル拡張子 */
  public static final String TSV_EXTENTION = ".tsv";

  /** 数値型日付．最小値 */
  public static final Integer INTEGET_DATE_MIN = 0;

  /** 数値型日付．最大値 */
  public static final Integer INTEGET_DATE_MAX = 99999999;

  /** 日付．最小日付 */
  public static final LocalDate LOCAL_DATE_MIN = LocalDate.of(1, 1, 1);

  /** 日付．最大日付 */
  public static final LocalDate LOCAL_DATE_MAX = LocalDate.of(9999, 12, 31);

  /** 処理状況.作成中 */
  public static final String SHORI_JOKYO_SAKUSEICHU = "1";

  /** 処理状況.確定 */
  public static final String SHORI_JOKYO_KAKUTEI = "2";

  /** 処理状況.確定（未承認仕訳有） */
  public static final String SHORI_JOKYO_KAKUTEI_MISHONIN_SHIWAKE_ARI = "3";

  /** 処理状況.仮確定 */
  public static final String SHORI_JOKYO_KARIKAKUTEI = "4";

  /** 部門提供機能確定状況.作成中 */
  public static final String BUMON_TEIKYO_KINO_KAKUTEI_JOKYO_SAKUSEICHU = "1";

  /** 部門提供機能確定状況.確定 */
  public static final String BUMON_TEIKYO_KINO_KAKUTEI_JOKYO_KAKUTEI = "2";

  /** 取込ファイル */
  public static final String TORIKOMI_FILE = "取込ファイル";

  // --------------------------------------------------
  // 日付フォーマット関連
  // --------------------------------------------------
  /** 日付フォーマット（yyyyMMdd） */
  public static final String DATE_FMT_YYYYMMDD = "yyyyMMdd";

  /** リセットフラグ.リセット無 */
  public static final String RESET_FLG_NASHI = "0";

  /** リセットフラグ.リセット有 */
  public static final String RESET_FLG_ARI = "1";

  /** 採番ルール.添付ファイルID */
  public static final String SAIBAN_RULE_TENPU_FILE_ID = "15";

  /** サイト区分.日数 */
  public static final String SITE_KBN_NISSU = "1";

  /** サイト区分.月日 */
  public static final String SITE_KBN_TUKIHI = "2";

  /** コード分類.サイト区分 */
  public static final String CD_BUNRUI_SITE_KBN = "B426";

  /** コード分類.サイト月 */
  public static final String CD_BUNRUI_SITE_TSUKI = "S001";

  /** コード分類.サイト日 */
  public static final String CD_BUNRUI_SITEBI = "S010";

  /** コード分類.回収支払区分 */
  public static final String KAISHU_SHIHARAI_KBN = "100000";

  /** コード分類.入力パターンコード */
  public static final String NYURYOKU_PATTERN_CD = "100100";

  /** コード分類.取扱区分 */
  public static final String TORIATSUKAI_KBN = "100200";

  /** コード分類.棚卸科目区分 */
  public static final String TANAOROSHI_KAMOKU_KBN = "100300";

  /** コード分類.共計区分 */
  public static final String KYOKEI_KBN = "100400";

  /** コード分類.費用収益発生フラグ */
  public static final String HIYO_SHUEKI_HASSEI_FLG = "100500";

  /** コード分類.自動取消区分 */
  public static final String JIDO_TORIKESHI_KBN = "100600";



  /** サイト分類.サイト区分 */
  public static final String SITE_BUNRUI_SITE_KBN = "00";

  /** サイト分類.起算日区分 */
  public static final String SITE_BUNRUI_KISANBI_KBN = "01";

  /** サイト分類.手形満期日区分 */
  public static final String SITE_BUNRUI_TEGATA_MANKIBI_KBN = "02";

  /** xmlファイルアップロード時のMimeタイプ */
  public static final String XML_MIME_TYPE = "text/xml";

}
