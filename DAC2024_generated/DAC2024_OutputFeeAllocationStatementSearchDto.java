/*
 * ClassName   DAC2024_OutputFeeAllocationStatementSearchDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     手数料配分計算書出力 検索条件Dto
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2024;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 手数料配分計算書出力 検索条件Dto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2024_OutputFeeAllocationStatementSearchDto {

  /** 出力帳票ID */
  private String outputChohyoId;

  /** 基準年月 (YYYYMM) */
  private Integer kijunNengetsu;

  /** 基準年月末日 (YYYYMMDD) */
  private Integer kijunNengetsuMatsujitsu;

  /** 業種コード */
  private String gyoshuCd;

  /** 業種名称 */
  private String gyoshuMei;

  /** 選択可能業種コードリスト */
  private List<String> selectableGyoshuCdList;

  /** 参照権限区分 */
  private String sanshoKengen;

  /** 所属コード */
  private String shozokuCd;
}
