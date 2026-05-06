/*
 * ClassName   DAC2024_PurchaseFeeAllocationStatementCsvDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     購買手数料配分計算書 CSV出力Dto
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2024;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 購買手数料配分計算書 CSV出力Dto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2024_PurchaseFeeAllocationStatementCsvDto {

  /** 対象年月 */
  private Integer taishoNengetsu;

  /** 業種コード */
  private String gyoshuCd;

  /** 業種名称 */
  private String gyoshuMei;

  /** 購買取扱高 */
  private BigDecimal kobaiToriatsukaidaka;

  /** 購買手数料率 */
  private BigDecimal kobaiTesuryoRitsu;

  /** 購買手数料 */
  private BigDecimal kobaiTesuryo;

  /** 購買取扱高（累計） */
  private BigDecimal kobaiToriatsukaidakaRuikei;

  /** 購買手数料率（累計） */
  private BigDecimal kobaiTesuryoRitsuRuikei;

  /** 購買手数料（累計） */
  private BigDecimal kobaiTesuryoRuikei;

  /** 組合員等購買取扱高 */
  private BigDecimal kumiaiin_kobaiToriatsukaidaka;

  /** 組合員等購買手数料率 */
  private BigDecimal kumiaiin_kobaiTesuryoRitsu;

  /** 組合員等購買手数料 */
  private BigDecimal kumiaiin_kobaiTesuryo;

  /** 組合員等購買取扱高（累計） */
  private BigDecimal kumiaiin_kobaiToriatsukaidakaRuikei;

  /** 組合員等購買手数料率（累計） */
  private BigDecimal kumiaiin_kobaiTesuryoRitsuRuikei;

  /** 組合員等購買手数料（累計） */
  private BigDecimal kumiaiin_kobaiTesuryoRuikei;

  /** 生活物資取扱高 */
  private BigDecimal seikatsumono_toriatsukaidaka;

  /** 生活物資手数料率 */
  private BigDecimal seikatsumono_tesuryoRitsu;

  /** 生活物資手数料 */
  private BigDecimal seikatsumono_tesuryo;

  /** 生活物資取扱高（累計） */
  private BigDecimal seikatsumono_toriatsukaidakaRuikei;

  /** 生活物資手数料率（累計） */
  private BigDecimal seikatsumono_tesuryoRitsuRuikei;

  /** 生活物資手数料（累計） */
  private BigDecimal seikatsumono_tesuryoRuikei;

  /** 燃料取扱高 */
  private BigDecimal nenryo_toriatsukaidaka;

  /** 燃料手数料率 */
  private BigDecimal nenryo_tesuryoRitsu;

  /** 燃料手数料 */
  private BigDecimal nenryo_tesuryo;

  /** 燃料取扱高（累計） */
  private BigDecimal nenryo_toriatsukaidakaRuikei;

  /** 燃料手数料率（累計） */
  private BigDecimal nenryo_tesuryoRitsuRuikei;

  /** 燃料手数料（累計） */
  private BigDecimal nenryo_tesuryoRuikei;

  /** 在庫品取扱高 */
  private BigDecimal zaikohin_toriatsukaidaka;

  /** 在庫品手数料率 */
  private BigDecimal zaikohin_tesuryoRitsu;

  /** 在庫品手数料 */
  private BigDecimal zaikohin_tesuryo;

  /** 在庫品取扱高（累計） */
  private BigDecimal zaikohin_toriatsukaidakaRuikei;

  /** 在庫品手数料率（累計） */
  private BigDecimal zaikohin_tesuryoRitsuRuikei;

  /** 在庫品手数料（累計） */
  private BigDecimal zaikohinTesuryoRitsuRuikei;
}
