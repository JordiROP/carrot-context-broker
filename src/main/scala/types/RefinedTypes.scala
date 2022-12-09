package org.onru.carrotcb
package types

import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.Uri

object RefinedTypes {
  type Id = String Refined Uri
}
