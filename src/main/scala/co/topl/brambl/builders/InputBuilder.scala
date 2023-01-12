package co.topl.brambl.builders

import co.topl.brambl.builders.BuilderErrors.InputBuilderError
import co.topl.brambl.builders.Models.InputBuildRequest
import co.topl.brambl.models.transaction.SpentTransactionOutput

/**
 * A trait that defines the functionality of a builder for IoTransaction inputs (SpentTransactionOutput)
 */
trait InputBuilder {
  /**
   *
   * Construct an unproven IoTransaction input (SpentTransactionOutput).
   * A SpentTransactionOutput spends an existing UnspentTransactionOutput.
   *
   * @param data The data required to build a SpentTransactionOutput
   *             The data is an object with the following fields:
   *             idx: Indices - Indices associated to an existing IoTransaction output for which the built input is spending.
   *             datum: Option[Datum.SpentOutput] - Additional data to include in the built SpentTransactionOutput
   * @return Either a InputBuilderError or the built SpentTransactionOutput
   */
  def constructUnprovenInput(data: InputBuildRequest): Either[InputBuilderError, SpentTransactionOutput]
}