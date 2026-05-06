/*
 * ClassName   DAC2024_ZendoKyokeiFeeAllocationStatementCsvDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     全道共計手数料配分計算書 CSV出力Dto
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
 * 全道共計手数料配分計算書 CSV出力Dto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2024_ZendoKyokeiFeeAllocationStatementCsvDto {

  /** 対象年月 */
  private Integer taishoNengetsu;

  /** 業種コード */
  private String gyoshuCd;

  /** 業種名称 */
  private String gyoshuMei;

  /** 取扱高 */
  private BigDecimal toriatsukaidaka;

  /** 手数料率 */
  private BigDecimal tesuryoRitsu;

  /** 手数料 */
  private BigDecimal tesuryo;

  /** 取扱高（累計） */
  private BigDecimal toriatsukaidakaRuikei;

  /** 手数料（累計） */
  private BigDecimal tesuryoRuikei;

  /** 組合員等取扱高 */
  private BigDecimal kumiaiin_toriatsukaidaka;

  /** 組合員等手数料率 */
  private BigDecimal kumiaiin_tesuryoRitsu;

  /** 組合員等手数料 */
  private BigDecimal kumiaiin_tesuryo;

  /** 組合員等取扱高（累計） */
  private BigDecimal kumiaiin_toriatsukaidakaRuikei;

  /** 組合員等手数料率（累計） */
  private BigDecimal kumiaiin_tesuryoRitsuRuikei;

  /** 組合員等手数料（累計） */
  private BigDecimal toriatsukaidakaTesuryoRuikei;
}
