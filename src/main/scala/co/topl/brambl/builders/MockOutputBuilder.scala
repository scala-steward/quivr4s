package co.topl.brambl.builders

import cats.implicits.catsSyntaxOptionId
import co.topl.brambl.builders.BuilderErrors.OutputBuilderError
import co.topl.brambl.builders.Models.OutputBuildRequest
import co.topl.brambl.models.transaction.UnspentTransactionOutput
import co.topl.brambl.models.{Address, Datum, Event, Identifier}
import co.topl.brambl.typeclasses.ContainsEvidence._
import co.topl.brambl.typeclasses.ContainsSignable.instances.lockSignable
import com.google.protobuf.ByteString
import quivr.models.SmallData

/**
 * A mock implementation of an [[OutputBuilder]]
 */
object MockOutputBuilder extends OutputBuilder {

  private final val EmptyData = SmallData(ByteString.EMPTY)

  // TODO: Replace with non-hardcoded values
  val Network = 0
  val Ledger = 0

  override def constructOutput(data: OutputBuildRequest): Either[OutputBuilderError, UnspentTransactionOutput] = {
    val address = Address(Network, Ledger,
      Identifier().withLock32(Identifier.Lock32(data.lock.sized32Evidence.some)).some
    )
    val value = data.value
    val datum = Datum.UnspentOutput(Event.UnspentTransactionOutput(
      if(data.metadata.isDefined) data.metadata else EmptyData.some
    ).some)
    Right(UnspentTransactionOutput(address.some, value.some, datum.some))
  }
}