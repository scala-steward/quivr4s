package co.topl.brambl

import co.topl.brambl.digests.{Blake2b256Digest, Hash}
import co.topl.brambl.signatures.{Curve25519Signature, Signing}
import co.topl.node.transaction.IoTransaction
import co.topl.node.typeclasses.ContainsSignable.instances.ioTransactionSignable
import co.topl.quivr.SignableBytes
import co.topl.quivr.runtime.{Datum, DynamicContext}
import co.topl.quivr.algebras.{DigestVerifier, SignatureVerifier}

// A Verification Context opinionated to the Topl context.
// signableBytes, currentTick and the datums are dynamic
case class Context(tx: IoTransaction, curTick: Long, heightDatums: String => Option[Datum[_]]) extends DynamicContext[Option, String] {
  override val hashingRoutines: Map[String, DigestVerifier[Option] with Hash] =
    Map("blake2b256" -> Blake2b256Digest)
  override val signingRoutines: Map[String, SignatureVerifier[Option] with Signing] =
    Map("curve25519" -> Curve25519Signature)
  override val interfaces = Map() // Arbitrary


  override def signableBytes: Option[SignableBytes] = Option(ioTransactionSignable.signableBytes(tx))

  override def currentTick: Option[Long] = Some(curTick)

  // Needed for height
  override val datums: String => Option[Datum[_]] = heightDatums
}