package co.topl.brambl.builders

import cats.implicits.catsSyntaxOptionId
import co.topl.brambl.builders.Models.InputBuildRequest
import co.topl.brambl.models.box.Lock
import co.topl.brambl.models.transaction.{Attestation, SpentTransactionOutput}
import co.topl.brambl.wallet.MockStorage
import quivr.models.Proof


object MockInputBuilder extends InputBuilder {
  private def constructUnprovenAttestation(lock: Lock): Either[BuilderError, Attestation] = lock.value match {
    case Lock.Value.Predicate(p) => Right(
      Attestation().withPredicate(
        Attestation.Predicate(
          p.some,
          List.fill(p.challenges.length)(Proof()) // Its unproven
        )
      )
    )
    case _ => Left(BuilderErrors.InputBuilderError("Only considering Predicate locks for now"))
  }
  override def constructUnprovenInput(data: InputBuildRequest): Either[BuilderError, SpentTransactionOutput] = {
    val id = MockStorage.getKnownIdentifierByIndices(data.idx)
    val box = id.flatMap(MockStorage.getBoxByKnownIdentifier)
    val attestation = box.flatMap(_.lock).map(constructUnprovenAttestation)
    val value = box.flatMap(_.value)
    val datum = data.datum
    val opts = List()
    (id, attestation, value) match {
      case (Some(knownId), Some(Right(att)), Some(boxVal)) =>
        Right(SpentTransactionOutput(knownId.some, att.some, boxVal.some, datum, opts))
      case (_, Some(Left(err)), _) => Left(err)
      case _ =>
        Left(BuilderErrors.InputBuilderError("Could not construct input"))
    }
  }
}
