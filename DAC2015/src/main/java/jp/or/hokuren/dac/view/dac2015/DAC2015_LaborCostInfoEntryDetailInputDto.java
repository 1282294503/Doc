/*
 * ClassName   DAC2015_LaborCostInfoEntryDetailInputDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報明細入力Dto
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
 * 人件費情報明細入力Dto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2015_LaborCostInfoEntryDetailInputDto {

  // --- 給与支給情報　支給額 ---

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

  /** 排他カウンタ(役員報酬) */
  private Integer haitaCntYakuinHoushu;

  /** 排他カウンタ(役員通勤手当) */
  private Integer haitaCntYakuinTsukinTeate;

  /** 排他カウンタ(職員本俸) */
  private Integer haitaCntShokuinHonpo;

  /** 排他カウンタ(諸手当) */
  private Integer haitaCntShoteate;

  /** 排他カウンタ(通勤手当) */
  private Integer haitaCntTsukinTeate;

  /** 排他カウンタ(臨時者賃金) */
  private Integer haitaCntRinjishaChingin;

  /** 排他カウンタ(臨時者通勤手当) */
  private Integer haitaCntRinjishaTsukinTeate;

  /** 排他カウンタ(教育訓練費) */
  private Integer haitaCntKyoikuKunrenhi;

  /** 排他カウンタ(健保給付金) */
  private Integer haitaCntKempoKyufukin;

  /** 排他カウンタ(きずな配当金) */
  private Integer haitaCntKizunaHaitokin;

  // --- 給与控除情報　控除額 ---

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

  /** 給与控除振込額 */
  private BigDecimal kyuyoKojoFurikomiGaku;

  /** 現金支給額 */
  private BigDecimal genkinShikyuGaku;

  /** 仮受金 */
  private BigDecimal kariukekin;

  /** 赤支給差額 */
  private BigDecimal akashikyuSagaku;

  /** 排他カウンタ(本会住宅貸付金) */
  private Integer haitaCntHonkaiJutakuKashitsukekin;

  /** 排他カウンタ(普通貸付金) */
  private Integer haitaCntFutsuKashitsukekin;

  /** 排他カウンタ(特別貸付金) */
  private Integer haitaCntTokubetsuKashitsukekin;

  /** 排他カウンタ(諸控除額) */
  private Integer haitaCntShokojoGaku;

  /** 排他カウンタ(其他控除額) */
  private Integer haitaCntSonotaKojoGaku;

  /** 排他カウンタ(現物給与額) */
  private Integer haitaCntGenbutsuKyuyoGaku;

  /** 排他カウンタ(物品代) */
  private Integer haitaCntBuppindai;

  /** 排他カウンタ(食費) */
  private Integer haitaCntShokuhi;

  /** 排他カウンタ(ホクレン労組費) */
  private Integer haitaCntHokurenRosohi;

  /** 排他カウンタ(生命保険料) */
  private Integer haitaCntSeimeiHokenryo;

  /** 排他カウンタ(損害保険料) */
  private Integer haitaCntSongaiHokenryo;

  /** 排他カウンタ(きずな月掛金) */
  private Integer haitaCntKizunaTsukikakekin;

  /** 排他カウンタ(きずな臨掛金) */
  private Integer haitaCntKizunaRinkakekin;

  /** 排他カウンタ(健康保険料) */
  private Integer haitaCntKenkoHokenryo;

  /** 排他カウンタ(介護保険料) */
  private Integer haitaCntKaigoHokenryo;

  /** 排他カウンタ(年金保険料) */
  private Integer haitaCntNenkinHokenryo;

  /** 排他カウンタ(雇用保険料) */
  private Integer haitaCntKoyoHokenryo;

  /** 排他カウンタ(所得税) */
  private Integer haitaCntShotokuzei;

  /** 排他カウンタ(住民税) */
  private Integer haitaCntJuminzei;

  /** 排他カウンタ(控除額積立貯金) */
  private Integer haitaCntKojoGakuTsumitateChokin;

  /** 排他カウンタ(雑受取利息) */
  private Integer haitaCntZatsuuketoriRisoku;

  /** 排他カウンタ(住宅料) */
  private Integer haitaCntJutakuryo;

  /** 排他カウンタ(その他預り金) */
  private Integer haitaCntSonotaAzukarikin;

  /** 排他カウンタ(貯金担保徴収額) */
  private Integer haitaCntChokinTanpoChoshuGaku;

  /** 排他カウンタ(外部財産形成掛金) */
  private Integer haitaCntGaibuZaisanKeiseiKakekin;

  /** 排他カウンタ(給与控除振込額) */
  private Integer haitaCntKyuyoKojoFurikomiGaku;

  /** 排他カウンタ(現金支給額) */
  private Integer haitaCntGenkinShikyuGaku;

  /** 排他カウンタ(仮受金) */
  private Integer haitaCntKariukekin;

  /** 排他カウンタ(赤支給差額) */
  private Integer haitaCntAkashikyuSagaku;

  // --- 会負担法定福利費　会負担健保情報 ---

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

  /** 排他カウンタ(役員健康保険料) */
  private Integer haitaCntYakuinKenkoHokenryo;

  /** 排他カウンタ(役員介護保険料) */
  private Integer haitaCntYakuinKaigoHokenryo;

  /** 排他カウンタ(職員健康保険料) */
  private Integer haitaCntShokuinKenkoHokenryo;

  /** 排他カウンタ(職員介護保険料) */
  private Integer haitaCntShokuinKaigoHokenryo;

  /** 排他カウンタ(臨時健康保険料) */
  private Integer haitaCntRinjiKenkoHokenryo;

  /** 排他カウンタ(臨時介護保険料) */
  private Integer haitaCntRinjiKaigoHokenryo;

  // --- 会負担法定福利費　会負担年金情報 ---

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

  /** 排他カウンタ(役員厚生年金保険料) */
  private Integer haitaCntYakuinKoseiNenkinHokenryo;

  /** 排他カウンタ(役員子供子育て拠出金) */
  private Integer haitaCntYakuinKodomoKosodateKyoshutsukin;

  /** 排他カウンタ(職員厚生年金保険料) */
  private Integer haitaCntShokuinKoseiNenkinHokenryo;

  /** 排他カウンタ(職員子供子育て拠出金) */
  private Integer haitaCntShokuinKodomoKosodateKyoshutsukin;

  /** 排他カウンタ(臨時厚生年金保険料) */
  private Integer haitaCntRinjiKoseiNenkinHokenryo;

  /** 排他カウンタ(臨時子供子育て拠出金) */
  private Integer haitaCntRinjiKodomoKosodateKyoshutsukin;

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

  /** 排他カウンタ(支給額定期貯金) */
  private Integer haitaCntShikyuGakuTeikiChokin;

  /** 排他カウンタ(預貯金積立貯金) */
  private Integer haitaCntYochokinTsumitateChokin;

  /** 排他カウンタ(雑支払利息) */
  private Integer haitaCntZatsushiharaiRisoku;

  /** 排他カウンタ(給与徴収金) */
  private Integer haitaCntKyuyoChoshukin;

  /** 排他カウンタ(利息税国税) */
  private Integer haitaCntRisokuzeiKokuzei;

  /** 排他カウンタ(利息税地方税) */
  private Integer haitaCntRisokuzeiChihozei;

  /** 排他カウンタ(控除額定期貯金) */
  private Integer haitaCntKojoGakuTeikiChokin;

  /** 排他カウンタ(貯金担保貸付金) */
  private Integer haitaCntChokinTanpoKashitsukekin;

  /** 排他カウンタ(貯金貸付利息) */
  private Integer haitaCntChokinKashitsukeRisoku;

  /** 排他カウンタ(預貯金振込額) */
  private Integer haitaCntYochokinFurikomiGaku;
}
