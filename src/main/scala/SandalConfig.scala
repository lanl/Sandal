/*
 *
 *  Copyright (c) 2015, 2016 Los Alamos National Security, LLC
 *                          All rights reserved.
 *
 *  This file is part of the Sandal project. See the LICENSE.txt file at the
 *  top-level directory of this distribution.
 *
 */

import squants.space.Meters

import scala.io.Source

class ConfigBase
{
  var debug_level : Int = _
  var print : Boolean = _
  var event_reconstruction : Boolean = _ // is_ER
}

class ConfigSimulation
{
  var dt : Double = _ // dt
  var duration : Double = _ // sim_time
  var nparticles : Int = _ // nparticles
  var random_seed : Int = _ // iseed
  var write : Boolean = _ // flag_write_prtcl
  var write_interval : Double = _ // prtcl_write_interval
  var run_id : String = _
}

class ConfigConc
{
  var avg_duration : Double = _ // avg_time_Cgrd
  var conc_write : Boolean = _ // flag_write_conc3D
  var dose_write : Boolean = _ // flag_write_dose3D
  var xyplane : Int = _ // flag_format_xyplane
  var conc_units : Int = _ // flag_conc_units
  var dose_units : Int = _ // flag_dose_units
  var ct_write : Boolean = _ // flag_write_ct
  var ref_level : Double = _ // ct_ref_level
  var ref_dose : Double = _ // ct_ref_dose
  var ref_dose_units : Int = _ // flag_ct_ref_dose_units
  var same_as_wind : Boolean = _ // flag_Cgrid_size
  var width : Double = _ // Lx_Cgrid
  var height : Double = _ // Ly_Cgrid
  var depth : Double = _ // Lz_Cgrid
  var z : Double = _ // z_lower_Cgrid
  var dx : Double = _ // dx_Cgrid
  var dy : Double = _ // dy_Cgrid
  var dz : Double = _ // dz_Cgrid
  var origin_units : Int = _ // flag_Cgrid_coord
  var x : Double = _ // x_west_Cgrid
  var y : Double = _ // y_south_Cgrid
}

class ConfigReceptor
{
  var enabled : Boolean = _ // flag_receptor
  var avg_duration : Double = _ // avg_time_rcptr
  var cell_size : Double = _ // rcptr_cell_size
  var cell_depth : Double = _ // rcptr_cell_depth
  var number : Int = _ // nreceptors
  var units : Int = _ // flag_receptor_coord
  var xs : Array[Double] = _
  var ys : Array[Double] = _
}

class ConfigWind
{
  var input_wind : Boolean = _ // flag_wind_input
  var constant_wind : Boolean = _ // flag_wind_profile
  var speed : Double = _ // WSref
  var direction : Double = _ // WDref
  var height : Double = _ // Zref
  var exponent : Double = _ // Plaw
}

class ConfigTurb
{
  var constant : Boolean = _ // flag_turb
  var drift : Boolean = _ // flag_drift
  var calm : Boolean = _
}

class ConfigTurbConstant
{
  var time_scale : Double = _ // TLw
  var stddev_w : Double = _ // sigw
  var stddev_v : Double = _ // sigv
  var stddev_u : Double = _ // sigu
}

class ConfigTurbMOSPGT
{
  var number : Int = _
  var filename : String = _
  var derive_mo : Boolean = _ // flag_MO_length
  var stability_class : Int = _ // stability_class
  var user_mo : Double = _ // MO_length
  var derive_roughness : Boolean = _ // flag_roughness
  var roughness_class : Int = _ // roughness_class
  var user_roughness : Double = _ // z0
  var height : Double = _ // BL_depth
}

class ConfigSource
{
  var mass : Double = _ // mass
  var cfu : Double = _ // cfu_per_gram
  var release : Int = _ // flag_release_duration
  var duration : Double = _ // release_duration
  var source : Int = _ // flag_source_geometry
  var units : Int = _ // flag_source_coord
  var deposition : Boolean = _ // flag_deposition
  var velocity : Double = _ // dep_velocity
  var decay : Boolean = _ // flag_decay
  var rate : Double = _ // decay_rate
}

class ConfigSourcePoint
{
  var position: Position3 = _
}

class ConfigSourceLines
{
  var number : Int = _ // nsegments
  // TODO don't know how to parse this if there are more than 0
}

class ConfigSourceRects
{
  var number : Int = _ // nrectangles
  var xs : Array[Double] = _ // xSW_rect
  var ys : Array[Double] = _ // ySW_rect
  var widths : Array[Double] = _ // rect_EWlength
  var heights : Array[Double] = _ // rect_NSlength
  var zs : Array[Double] = _ // zht_rectangle
}

class ConfigSourceCircles
{
  var number : Int = _ // ncircles
  var xs : Array[Double] = _ // xcenter
  var ys : Array[Double] = _ // ycenter
  var radii : Array[Double] = _ // circle_radius
  var zs : Array[Double] = _ // zht_circle
}

class SandalConfig()
{
  val base : ConfigBase = new ConfigBase
  val sim : ConfigSimulation = new ConfigSimulation
  val conc : ConfigConc = new ConfigConc
  val receptor : ConfigReceptor = new ConfigReceptor
  val wind : ConfigWind = new ConfigWind
  val turb : ConfigTurb = new ConfigTurb
  val turb_constant : ConfigTurbConstant = new ConfigTurbConstant
  val turb_mospgt : ConfigTurbMOSPGT = new ConfigTurbMOSPGT
  val source : ConfigSource = new ConfigSource
  val source_point : ConfigSourcePoint = new ConfigSourcePoint
  val source_lines : ConfigSourceLines = new ConfigSourceLines
  val source_rects : ConfigSourceRects = new ConfigSourceRects
  val source_circles : ConfigSourceCircles = new ConfigSourceCircles
}

object SandalConfigReader {
  def apply(filename: String) : SandalConfig = {
    val lines : Iterator[String] = Source.fromFile(filename).getLines()
    val config : SandalConfig = new SandalConfig()

    // skip 6 comment lines
    for (i <- 1 to 6)
    {
      lines.next()
    }

    // CONFIGURATION PARAMETER
    config.base.debug_level = lines.next().trim.split("\\s").head.toInt

    lines.next().trim.split("\\s").head.toInt match
    {
      case 0 => config.base.print = false
      case 1 => config.base.print = true
      case n => throw new IllegalArgumentException(
        s"""Invalid visualization on standard output.
           |Admitted values [0 1]. Assigned value: $n""".stripMargin)
    }

    lines.next().trim.split("\\s").head.toInt match
    {
      case 0 => config.base.event_reconstruction = false
      case 1 => config.base.event_reconstruction = true
      case n => throw new IllegalArgumentException(
        s"""Invalid calculation mode.
           |Admitted values [0 1]. Assigned value: $n""".stripMargin)
    }

    // skip 3 comment lines
    for (i <- 1 to 3)
    {
      lines.next()
    }

    // RANDOM-WALK SIMULATION PARAMETERS
    config.sim.dt = lines.next().trim.split("\\s").head.toDouble
    config.sim.duration = lines.next().trim.split("\\s").head.toDouble
    config.sim.nparticles = lines.next().trim.split("\\s").head.toDouble.toInt
    config.sim.random_seed = lines.next().trim.split("\\s").head.toInt
    lines.next().trim.split("\\s").head.toInt match
    {
      case 2 => config.sim.write = false
      case 1 => config.sim.write = true
      case n => throw new IllegalArgumentException // TODO
    }
    config.sim.write_interval = lines.next().trim.split("\\s").head.toDouble
    config.sim.run_id = lines.next().trim.split("\\s").head

    // skip 3
    for (i <- 1 to 3)
    {
      lines.next()
    }

    // CONCENTRATION GRID SAMPLING PARAMETERS
    config.conc.avg_duration = lines.next().trim.split("\\s").head.toDouble
    lines.next().trim.split("\\s").head.toInt match
    {
      case 2 => config.conc.conc_write = false
      case 1 => config.conc.conc_write = true
      case n => throw new IllegalArgumentException // TODO
    }
    lines.next().trim.split("\\s").head.toInt match
    {
      case 2 => config.conc.dose_write = false
      case 1 => config.conc.dose_write = true
      case n => throw new IllegalArgumentException // TODO
    }
    config.conc.xyplane = lines.next().trim.split("\\s").head.toInt
    config.conc.conc_units = lines.next().trim.split("\\s").head.toInt
    config.conc.dose_units = lines.next().trim.split("\\s").head.toInt
    lines.next().trim.split("\\s").head.toInt match
    {
      case 1 => config.conc.ct_write = false
      case 2 => config.conc.ct_write = true
      case n => throw new IllegalArgumentException // TODO
    }
    config.conc.ref_level = lines.next().trim.split("\\s").head.toDouble
    config.conc.ref_dose = lines.next().trim.split("\\s").head.toDouble
    config.conc.ref_dose_units = lines.next().trim.split("\\s").head.toInt
    lines.next().trim.split("\\s").head.toInt match
    {
      case 1 => config.conc.same_as_wind = false
      case 2 => config.conc.same_as_wind = true
      case n => throw new IllegalArgumentException // TODO
    }
    config.conc.width = lines.next().trim.split("\\s").head.toDouble
    config.conc.height = lines.next().trim.split("\\s").head.toDouble
    config.conc.depth = lines.next().trim.split("\\s").head.toDouble
    config.conc.z = lines.next().trim.split("\\s").head.toDouble
    config.conc.dx = lines.next().trim.split("\\s").head.toDouble
    config.conc.dy = lines.next().trim.split("\\s").head.toDouble
    config.conc.dz = lines.next().trim.split("\\s").head.toDouble
    config.conc.origin_units = lines.next().trim.split("\\s").head.toInt
    config.conc.x = lines.next().trim.split("\\s").head.toDouble
    config.conc.y = lines.next().trim.split("\\s").head.toDouble

    // skip 3
    for (i <- 1 to 3)
    {
      lines.next()
    }

    // RECEPTOR/COLLECTOR PARAMETERS
    lines.next().trim.split("\\s").head.toInt match
    {
      case 1 => config.receptor.enabled = true
      case 2 => config.receptor.enabled = false
      case n => throw new IllegalArgumentException // TODO
    }
    config.receptor.avg_duration = lines.next().trim.split("\\s").head.toDouble
    config.receptor.cell_size = lines.next().trim.split("\\s").head.toDouble
    config.receptor.cell_depth = lines.next().trim.split("\\s").head.toDouble
    config.receptor.number = lines.next().trim.split("\\s").head.toInt
    config.receptor.units = lines.next().trim.split("\\s").head.toInt
    config.receptor.xs = new Array[Double](config.receptor.number)
    config.receptor.ys = new Array[Double](config.receptor.number)
    for (i <- 0 to config.receptor.number - 1)
    {
      lines.next()
      config.receptor.xs(i) = lines.next().trim.split("\\s").head.toDouble
      config.receptor.ys(i) = lines.next().trim.split("\\s").head.toDouble
    }

    // skip 3
    for (i <- 1 to 3)
    {
      lines.next()
    }

    // MEAN WIND PARAMETERS
    lines.next().trim.split("\\s").head.toInt match
    {
      case 1 => config.wind.input_wind = false
      case 2 => config.wind.input_wind = true
      case n => throw new IllegalArgumentException // TODO
    }
    lines.next().trim.split("\\s").head.toInt match
    {
      case 1 => config.wind.constant_wind = true
      case 2 => config.wind.constant_wind = false
      case n => throw new IllegalArgumentException // TODO
    }
    config.wind.speed = lines.next().trim.split("\\s").head.toDouble
    config.wind.direction = lines.next().trim.split("\\s").head.toDouble
    config.wind.height = lines.next().trim.split("\\s").head.toDouble
    config.wind.exponent = lines.next().trim.split("\\s").head.toDouble

    // skip 3
    for (i <- 1 to 3)
    {
      lines.next()
    }

    // TURBULENCE PARAMETERS
    lines.next().trim.split("\\s").head.toInt match
    {
      case 1 => config.turb.constant = true
      case 2 => config.turb.constant = false
      case n => throw new IllegalArgumentException // TODO
    }
    lines.next().trim.split("\\s").head.toInt match
    {
      case 0 => config.turb.drift = false
      case 1 => config.turb.drift = true
      case n => throw new IllegalArgumentException // TODO
    }
    lines.next().trim.split("\\s").head.toInt match
    {
      case 0 => config.turb.calm = false
      case 1 => config.turb.calm = true
      case n => throw new IllegalArgumentException // TODO
    }

    // skip 3
    for (i <- 1 to 3)
    {
      lines.next()
    }

    // CONSTANT VALUES (turb_flag = 1)
    config.turb_constant.time_scale = lines.next().trim.split("\\s").head.toDouble
    config.turb_constant.stddev_w = lines.next().trim.split("\\s").head.toDouble
    config.turb_constant.stddev_v = lines.next().trim.split("\\s").head.toDouble
    config.turb_constant.stddev_u = lines.next().trim.split("\\s").head.toDouble

    // skip 3
    for (i <- 1 to 3)
    {
      lines.next()
    }

    // M-O SIMILARITY & PGT CLASSES (turb_flag = 2)
    config.turb_mospgt.number = lines.next().trim.split("\\s").head.toInt
    config.turb_mospgt.filename = lines.next().trim.split("\\s").head
    lines.next().trim.split("\\s").head.toInt match
    {
      case 1 => config.turb_mospgt.derive_mo = true
      case 2 => config.turb_mospgt.derive_mo = false
      case n => throw new IllegalArgumentException // TODO
    }
    config.turb_mospgt.stability_class = lines.next().trim.split("\\s").head.toInt
    config.turb_mospgt.user_mo = lines.next().trim.split("\\s").head.toDouble
    lines.next().trim.split("\\s").head.toInt match // TODO
    {
      case 1 => config.turb_mospgt.derive_roughness = true
      case 2 => config.turb_mospgt.derive_roughness = false
      case n => throw new IllegalArgumentException // TODO
    }
    config.turb_mospgt.roughness_class = lines.next().trim.split("\\s").head.toInt
    config.turb_mospgt.user_roughness =
      lines.next().trim.split("\\s").head.toDouble
    config.turb_mospgt.height = lines.next().trim.split("\\s").head.toDouble

    // skip 3
    for (i <- 1 to 3)
    {
      lines.next()
    }

    // SOURCE PARAMETERS
    config.source.mass = lines.next().trim.split("\\s").head.toDouble
    config.source.cfu = lines.next().trim.split("\\s").head.toDouble
    config.source.release = lines.next().trim.split("\\s").head.toInt
    config.source.duration = lines.next().trim.split("\\s").head.toDouble
    config.source.source = lines.next().trim.split("\\s").head.toInt
    config.source.units = lines.next().trim.split("\\s").head.toInt
    lines.next().trim.split("\\s").head.toInt match
    {
      case 1 => config.source.deposition = false
      case 2 => config.source.deposition = true
      case n => throw new IllegalArgumentException // TODO
    }
    config.source.velocity = lines.next().trim.split("\\s").head.toDouble
    lines.next().trim.split("\\s").head.toInt match
    {
      case 1 => config.source.decay = false
      case 2 => config.source.decay = true
      case n => throw new IllegalArgumentException // TODO
    }
    config.source.rate = lines.next().trim.split("\\s").head.toDouble

    // skip 3
    for (i <- 1 to 3)
    {
      lines.next()
    }

    // POINT SOURCE
    config.source_point.position = Position3(
      Meters(lines.next().trim.split("\\s").head.toDouble),
      Meters(lines.next().trim.split("\\s").head.toDouble),
      Meters(lines.next().trim.split("\\s").head.toDouble))

    // skip 3
    for (i <- 1 to 3)
    {
      lines.next()
    }

    // LINE SOURCE
    // TODO don't know how to parse this if there is more than 0
    config.source_lines.number = lines.next().trim.split("\\s").head.toInt

    // skip 3
    for (i <- 1 to 3)
    {
      lines.next()
    }

    // RECTANGULAR AREA SOURCE
    config.source_rects.number = lines.next().trim.split("\\s").head.toInt
    config.source_rects.xs = new Array[Double](config.source_rects.number)
    config.source_rects.ys = new Array[Double](config.source_rects.number)
    config.source_rects.widths = new Array[Double](config.source_rects.number)
    config.source_rects.heights = new Array[Double](config.source_rects.number)
    config.source_rects.zs = new Array[Double](config.source_rects.number)
    for (i <- 0 to config.source_rects.number - 1)
    {
      config.source_rects.xs(i) = lines.next().trim.split("\\s").head.toDouble
      config.source_rects.ys(i) = lines.next().trim.split("\\s").head.toDouble
      config.source_rects.widths(i) = lines.next().trim.split("\\s").head.toDouble
      config.source_rects.heights(i) =
        lines.next().trim.split("\\s").head.toDouble
      config.source_rects.zs(i) = lines.next().trim.split("\\s").head.toDouble
    }

    // skip 3
    for (i <- 1 to 3)
    {
      lines.next()
    }

    // CIRCULAR AREA SOURCE
    config.source_circles.number = lines.next().trim.split("\\s").head.toInt
    config.source_circles.xs = new Array[Double](config.source_circles.number)
    config.source_circles.ys = new Array[Double](config.source_circles.number)
    config.source_circles.radii =
      new Array[Double](config.source_circles.number)
    config.source_circles.zs = new Array[Double](config.source_circles.number)
    for (i <- 0 to config.source_circles.number - 1)
    {
      config.source_circles.xs(i) = lines.next().trim.split("\\s").head.toDouble
      config.source_circles.ys(i) = lines.next().trim.split("\\s").head.toDouble
      config.source_circles.radii(i) =
        lines.next().trim.split("\\s").head.toDouble
      config.source_circles.zs(i) = lines.next().trim.split("\\s").head.toDouble
    }

    config
  }
}
