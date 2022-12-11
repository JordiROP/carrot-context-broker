package org.onru.carrotcb

import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.Uri

package object types {
  type Id = String Refined Uri
}
