package co.topl.node

sealed abstract class Event

object Events {
  case class Eon(beginSlot: Long, height: Long) extends Event
  case class Era(beginSlot: Long, height: Long) extends Event
  case class Epoch(beginSlot: Long, height: Long) extends Event
  case class Header(height: Long) extends Event

  case class Root(value: Root) extends Event

  case class IoTransaction(
    schedule:     co.topl.node.transaction.IoTransaction.Schedule,
    references32: List[KnownIdentifiers.TransactionOutput32],
    references64: List[KnownIdentifiers.TransactionOutput64],
    metadata:     SmallData
  ) extends Event

  case class SpentTransactionOutput(
    references32: List[KnownReferences.Predicate32],
    references64: List[KnownReferences.Predicate64],
    metadata:     SmallData
  ) extends Event

  case class UnspentTransactionOutput(
    references32: List[KnownReferences.Blob32],
    references64: List[KnownReferences.Blob64],
    metadata:     SmallData
  ) extends Event
}
