/*
 * ClassName   DAC2015_LaborCostInfoEntryBasicInputDto
 *
 * Author      SCSK北海道株式会社
 *
 * Create date 2026/03/31
 *
 * Process     人件費情報基本入力Dto
 *
 * Notes
 *
 * History
 *
 */

package jp.or.hokuren.dac.view.dac2015;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.primefaces.model.file.UploadedFile;

/**
 * <pre>
 * 人件費情報基本入力Dto
 * </pre>
 *
 * @author SCSK北海道株式会社
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DAC2015_LaborCostInfoEntryBasicInputDto {

  /** 支給日 */
  private LocalDate shikyubi;

  /** 添付ファイルＩＤ１ */
  private Integer tenpuFileId1;

  /** 添付ファイル１ */
  private UploadedFile tenpuFile1;

  /** 添付ファイルＩＤ２ */
  private Integer tenpuFileId2;

  /** 添付ファイル２ */
  private UploadedFile tenpuFile2;

  /** 添付ファイルＩＤ３ */
  private Integer tenpuFileId3;

  /** 添付ファイル３ */
  private UploadedFile tenpuFile3;

  /** 添付ファイル数 */
  private Integer tenpuFileSuu;

  /** 参照URL */
  private String sanshoUrl;

  /** 排他カウンタ */
  private Integer haitaCnt;

  /** 排他カウンタ（給与控除） */
  private Integer haitaCntKyuyoKojo;
}
