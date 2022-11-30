package co.topl.node

sealed abstract class Event

object Events {
  case class Eon(beginSlot: Long, height: Long) extends Event
  case class Era(beginSlot: Long, height: Long) extends Event
  case class Epoch(beginSlot: Long, height: Long) extends Event
  case class Header(height: Long) extends Event
  case class Body(root: Root) extends Event

  case class IoTransaction(
    schedule:   co.topl.node.transaction.IoTransaction.Schedule,
    references: List[References.KnownSpendable32],
    metadata:   SmallData
  ) extends Event

  case class SpentOutput(
    references: List[References.KnownPredicate32],
    metadata:   SmallData
  ) extends Event

  case class UnspentOutput(
    references: List[References.KnownBlob32],
    metadata:   SmallData
  ) extends Event
}
