package com.sojpt.poi;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.jfinal.ext.kit.excel.PoiExporter;
import com.jfinal.ext.render.excel.PoiRender;
import com.jfinal.log.Log;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;

public class SoJPoiRender extends Render {

	protected final Log LOG = Log.getLog(getClass());
    private final static String CONTENT_TYPE = "application/msexcel;charset=" + getEncoding();
    private List<?>[] data;
    private String[][] headers;
    private String[] sheetNames = new String[]{};
    private int cellWidth;
    private String[] columns = new String[]{};
    private String fileName = "file1.xls";
    private int headerRow;
    private String version;

    public SoJPoiRender(List<?>[] data) {
        this.data = data;
    }

    public static PoiRender me(List<?>... data) {
        return new PoiRender(data);
    }

    @Override
    public void render() {
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        response.setContentType(CONTENT_TYPE);
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            PoiExporter.data(data).version(version).sheetNames(sheetNames).headerRow(headerRow).headers(headers).columns(columns)
                    .cellWidth(cellWidth).export().write(os);
        } catch (Exception e) {
            throw new RenderException(e);
        } finally {
            try {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }

        }
    }

    public SoJPoiRender headers(String[]... headers) {
        this.headers = headers;
        return this;
    }

    public SoJPoiRender headerRow(int headerRow) {
        this.headerRow = headerRow;
        return this;
    }

    public SoJPoiRender columns(String... columns) {
        this.columns = columns;
        return this;
    }

    public SoJPoiRender sheetName(String... sheetName) {
        this.sheetNames = sheetName;
        return this;
    }

    public SoJPoiRender cellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
        return this;
    }

    public SoJPoiRender fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public SoJPoiRender version(String version) {
        this.version = version;
        return this;
    }
	
}
