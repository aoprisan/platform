/*
 *  ____    ____    _____    ____    ___     ____ 
 * |  _ \  |  _ \  | ____|  / ___|  / _/    / ___|        Precog (R)
 * | |_) | | |_) | |  _|   | |     | |  /| | |  _         Advanced Analytics Engine for NoSQL Data
 * |  __/  |  _ <  | |___  | |___  |/ _| | | |_| |        Copyright (C) 2010 - 2013 SlamData, Inc.
 * |_|     |_| \_\ |_____|  \____|   /__/   \____|        All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU Affero General Public License as published by the Free Software Foundation, either version 
 * 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See 
 * the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this 
 * program. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.precog.yggdrasil

trait Schema {
  sealed trait JType
  
  case object JNumberT extends JType
  case object JTextT extends JType
  case object JBooleanT extends JType
  case object JNullT extends JType
  
  sealed trait JArrayT extends JType
  case class JArrayFixedT(tpe: JType) extends JArrayT
  case object JArrayUnfixedT extends JArrayT

  sealed trait JObjectT extends JType
  case class JObjectFixedT(fields: Map[String, JType]) extends JObjectT
  case object JObjectUnfixedT extends JObjectT

  case class JUnionT(left: JType, right: JType) extends JType
  
  def flattenUnions(tpe: JType): Set[JType] = tpe match {
    case JUnionT(left, right) => flattenUnions(left) ++ flattenUnions(right)
    case t => Set(t)
  }
}
