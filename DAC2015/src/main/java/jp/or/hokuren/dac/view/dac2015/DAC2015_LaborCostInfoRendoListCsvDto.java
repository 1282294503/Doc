/*
 * ClassName   DAC2015_LaborCostInfoRendoListCsvDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報連動一覧 CSV出力Dto
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2015;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 人件費情報連動一覧 CSV出力Dto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2015_LaborCostInfoRendoListCsvDto {

  /** データ区分名称 */
  private String dataKbnMeisho;

  /** 支給日（YYYYMMDD形式） */
  private Integer shikyubi;

  /** 入力区分コード */
  private String nyuryokuKbnCd;

  /** 入力区分名称 */
  private String nyuryokuKbnMeisho;

  /** 給与区分コード */
  private String kyuyoKbnCd;

  /** 給与区分名称 */
  private String kyuyoKbnMeisho;

  /** 給与個所コード */
  private String kyuyoKashoCd;

  /** 給与個所名称 */
  private String kyuyoKashoMeisho;

  /** 科目分類コード */
  private String kamokuBunruiCd;

  /** 科目分類名称 */
  private String kamokuBunruiMeisho;

  /** 科目区分コード */
  private String kamokuKbnCd;

  /** 科目区分名称 */
  private String kamokuKbnMeisho;

  /** 業種コード */
  private String gyoshuCd;

  /** 業種名称 */
  private String gyoshuMeisho;

  /** 所属コード */
  private String shozokuCd;

  /** 所属名称 */
  private String shozokuMeisho;

  // --- 給与支給情報 ---

  /** 役員報酬 */
  private BigDecimal yakuinHoushu;

  /** 役員通勤手当 */
  private BigDecimal yakuinTsukinTeate;

  /** 職員本俸 */
  private BigDecimal shokuinHonpo;

  /** 諸手当 */
  private BigDecimal shoteate;

  /** 通勤手当 */
  private BigDecimal tsukinTeate;

  /** 臨時者賃金 */
  private BigDecimal rinjishaChingin;

  /** 臨時者通勤手当 */
  private BigDecimal rinjishaTsukinTeate;

  /** 教育訓練費 */
  private BigDecimal kyoikuKunrenhi;

  /** 健保給付金 */
  private BigDecimal kempoKyufukin;

  /** きずな配当金 */
  private BigDecimal kizunaHaitokin;

  // --- 給与控除情報 ---

  /** 本会住宅貸付金 */
  private BigDecimal honkaiJutakuKashitsukekin;

  /** 普通貸付金 */
  private BigDecimal futsuKashitsukekin;

  /** 特別貸付金 */
  private BigDecimal tokubetsuKashitsukekin;

  /** 諸控除額 */
  private BigDecimal shokojoGaku;

  /** 其他控除額 */
  private BigDecimal sonotaKojoGaku;

  /** 現物給与額 */
  private BigDecimal genbutsuKyuyoGaku;

  /** 物品代 */
  private BigDecimal buppindai;

  /** 食費 */
  private BigDecimal shokuhi;

  /** ホクレン労組費 */
  private BigDecimal hokurenRosohi;

  /** 生命保険料 */
  private BigDecimal seimeiHokenryo;

  /** 損害保険料 */
  private BigDecimal songaiHokenryo;

  /** きずな月掛金 */
  private BigDecimal kizunaTsukikakekin;

  /** きずな臨掛金 */
  private BigDecimal kizunaRinkakekin;

  /** 健康保険料 */
  private BigDecimal kenkoHokenryo;

  /** 介護保険料 */
  private BigDecimal kaigoHokenryo;

  /** 年金保険料 */
  private BigDecimal nenkinHokenryo;

  /** 雇用保険料 */
  private BigDecimal koyoHokenryo;

  /** 所得税 */
  private BigDecimal shotokuzei;

  /** 住民税 */
  private BigDecimal juminzei;

  /** 控除額積立貯金 */
  private BigDecimal kojoGakuTsumitateChokin;

  /** 雑受取利息 */
  private BigDecimal zatsuuketoriRisoku;

  /** 住宅料 */
  private BigDecimal jutakuryo;

  /** その他預り金 */
  private BigDecimal sonotaAzukarikin;

  /** 貯金担保徴収額 */
  private BigDecimal chokinTanpoChoshuGaku;

  /** 外部財産形成掛金 */
  private BigDecimal gaibuZaisanKeiseiKakekin;

  // --- 差引振込情報 ---

  /** 給与控除振込額 */
  private BigDecimal kyuyoKojoFurikomiGaku;

  /** 現金支給額 */
  private BigDecimal genkinShikyuGaku;

  /** 仮受金 */
  private BigDecimal kariukekin;

  /** 赤支給差額 */
  private BigDecimal akashikyuSagaku;

  // --- 会負担健保情報 ---

  /** 役員健康保険料 */
  private BigDecimal yakuinKenkoHokenryo;

  /** 役員介護保険料 */
  private BigDecimal yakuinKaigoHokenryo;

  /** 職員健康保険料 */
  private BigDecimal shokuinKenkoHokenryo;

  /** 職員介護保険料 */
  private BigDecimal shokuinKaigoHokenryo;

  /** 臨時健康保険料 */
  private BigDecimal rinjiKenkoHokenryo;

  /** 臨時介護保険料 */
  private BigDecimal rinjiKaigoHokenryo;

  // --- 会負担年金情報 ---

  /** 役員厚生年金保険料 */
  private BigDecimal yakuinKoseiNenkinHokenryo;

  /** 役員子供子育て拠出金 */
  private BigDecimal yakuinKodomoKosodateKyoshutsukin;

  /** 職員厚生年金保険料 */
  private BigDecimal shokuinKoseiNenkinHokenryo;

  /** 職員子供子育て拠出金 */
  private BigDecimal shokuinKodomoKosodateKyoshutsukin;

  /** 臨時厚生年金保険料 */
  private BigDecimal rinjiKoseiNenkinHokenryo;

  /** 臨時子供子育て拠出金 */
  private BigDecimal rinjiKodomoKosodateKyoshutsukin;

  // --- 預貯金情報 ---

  /** 支給額定期貯金 */
  private BigDecimal shikyuGakuTeikiChokin;

  /** 預貯金積立貯金 */
  private BigDecimal yochokinTsumitateChokin;

  /** 雑支払利息 */
  private BigDecimal zatsushiharaiRisoku;

  /** 給与徴収金 */
  private BigDecimal kyuyoChoshukin;

  /** 利息税国税 */
  private BigDecimal risokuzeiKokuzei;

  /** 利息税地方税 */
  private BigDecimal risokuzeiChihozei;

  /** 控除額定期貯金 */
  private BigDecimal kojoGakuTeikiChokin;

  /** 貯金担保貸付金 */
  private BigDecimal chokinTanpoKashitsukekin;

  /** 貯金貸付利息 */
  private BigDecimal chokinKashitsukeRisoku;

  /** 預貯金振込額 */
  private BigDecimal yochokinFurikomiGaku;
}
