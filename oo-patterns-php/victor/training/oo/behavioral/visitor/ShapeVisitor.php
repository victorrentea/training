<?php
/**
 * Created by IntelliJ IDEA.
 * User: VictorRentea
 * Date: 9/19/2017
 * Time: 12:42 AM
 */

namespace victor\training\oo\behavioral\visitor;


interface ShapeVisitor
{
    function visit(Shape $circle);

}