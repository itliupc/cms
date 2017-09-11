package com.cms.utils;

import java.util.List;

public class GridView<T>
{
  private List<T> rows;
  private long total;
  
  public List<T> getRows()
  {
    return this.rows;
  }
  
  public void setRows(List<T> rows)
  {
    this.rows = rows;
  }
  
  public long getTotal()
  {
    return this.total;
  }
  
  public void setTotal(long total)
  {
    this.total = total;
  }
  
  public GridView(List<T> rows, long total)
  {
    setRows(rows);
    setTotal(total);
  }
}
